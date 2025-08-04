// 위치: aivlebigproject/infra/ValidationResponse.java
package aivlebigproject.infra;

import java.util.List;
import lombok.Data;

@Data
public class ValidationResponse {
    // [수정] 필드 이름을 errors -> warnings 로 변경
    private List<AiValidationError> warnings;

    public ValidationResponse(List<AiValidationError> warnings) {
        this.warnings = warnings;
    }
}