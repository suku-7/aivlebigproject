// ========================================
// FILENAME: aivlebigproject/funeralcontext/src/main/java/aivlebigproject/infra/FuneralInfoAiValidator.java
// 역할 : OpenAI API를 호출하여 데이터의 상세 오탈자를 검증
// ========================================

package aivlebigproject.infra;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FuneralInfoAiValidator {

    @Value("${openai.api.key}")
    private String openaiApiKey;

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    // [수정] 반환 타입을 boolean에서 List<AiValidationError>로 변경
    public List<AiValidationError> validateData(Map<String, Object> fieldsToValidate) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String fieldsJson = objectMapper.writeValueAsString(fieldsToValidate);

            // [수정] 프롬프트를 상세 JSON 응답을 요청하도록 변경
            String prompt = 
                "당신은 데이터를 부드럽게 교정해주는 '친절한 조언가'입니다. " +
                "아래 JSON 데이터를 분석하여, 사람이 보기엔 어색하거나 실수로 보이는 항목들을 찾아 " +
                "'경고(warnings)'라는 배열로 반환해주세요. " +
                "각 경고는 다음 3가지 키를 포함해야 합니다: fieldName, warningDescription, suggestion. " +
                "경고가 없으면 빈 배열([])을 반환해주세요. " +
                "모든 응답은 반드시 한국어로 작성해주세요.\n\n" +

                "검토 규칙은 다음과 같습니다:\n" +
                "- reporterPhone: '010-XXXX-YYYY' 형식을 따라야 하며, 하이픈이 포함되어야 합니다.\n" +
                "- deceasedName: 부자연스러운 문자, 반복, 특수기호 등이 포함되어 있으면 경고합니다.\n" +
                "- deceasedRrn: 주민등록번호 형식이 맞는지 (YYMMDD-XXXXXXX), 생년월일과 일치하는지 확인합니다.\n" +
                "- deceasedBirthOfDate / deceasedDate: 생년월일이 사망일보다 이전이어야 합니다.\n" +
                "- deceasedAge: 생년월일 기준으로 계산된 나이와 입력된 나이가 유사한지 확인합니다.\n\n" +

                "분석할 데이터는 다음과 같습니다:\n" + fieldsJson;

            // // [수정] 프롬프트를 상세 JSON 응답을 요청하도록 변경
            // String prompt = "당신은 장례 서비스 시스템의 데이터 검증 전문가입니다. " +
            //         "다음 JSON 데이터를 분석하여 오탈자, 형식 오류, 논리적 오류를 찾아주세요. " +
            //         "결과는 'errors'라는 키를 가진 JSON 배열로 반환해주세요. 배열의 각 객체는 fieldName, errorDescription, suggestion 키를 가져야 합니다. " +
            //         "이때, errorDescription과 suggestion의 내용은 반드시 한국어로 작성해야 합니다. " + // <-- [추가] 한글 답변 지시사항
            //         "오류가 없으면 빈 배열([])을 반환해주세요. " +
            //         "데이터는 다음과 같습니다: \n" + fieldsJson;

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(openaiApiKey);

            Map<String, Object> message = new HashMap<>();
            message.put("role", "user");
            message.put("content", prompt);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-4o");
            requestBody.put("messages", Collections.singletonList(message));
            requestBody.put("response_format", Map.of("type", "json_object")); // JSON 응답 형식 강제
            requestBody.put("max_tokens", 500); // 충분한 토큰 할당
            requestBody.put("temperature", 0.0);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            Map<String, Object> response = restTemplate.postForObject(OPENAI_API_URL, entity, Map.class);

            // [수정] API 응답을 파싱하여 List<AiValidationError> 객체로 변환
            if (response != null && response.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> choice = choices.get(0);
                    Map<String, String> responseMessage = (Map<String, String>) choice.get("message");
                    String content = responseMessage.get("content");

                    System.out.println("AI 검증 응답 (JSON): " + content);
                    
                    // LLM이 반환한 JSON 문자열을 객체로 변환
                    Map<String, List<AiValidationError>> result = objectMapper.readValue(content, new TypeReference<>() {});
                    return result.getOrDefault("warnings", Collections.emptyList());
                }
            }
            return Collections.emptyList();

        } catch (Exception e) {
            e.printStackTrace();
            // [수정] API 호출 자체에 실패했을 때를 위한 비상 오류 객체를 생성하여 반환
            return Collections.singletonList(createFallbackError(e.getMessage()));
        }
    }
    
    private AiValidationError createFallbackError(String errorMessage) {
        AiValidationError error = new AiValidationError();
        error.setFieldName("system");
        error.setWarningDescription("AI 검증 시스템에 오류가 발생했습니다.");
        error.setSuggestion("잠시 후 다시 시도해주세요. 오류: " + errorMessage);
        return error;
    }
}