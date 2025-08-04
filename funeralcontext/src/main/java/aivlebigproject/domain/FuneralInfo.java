// ========================================
// FILENAME: aivlebigproject/funeralcontext/src/main/java/aivlebigproject/domain/FuneralInfo.java
// 역할 : 
// ========================================

package aivlebigproject.domain;

import aivlebigproject.FuneralcontextApplication;
import aivlebigproject.infra.FuneralInfoAiValidator;
import aivlebigproject.domain.FuneralInfoRegistered;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "FuneralInfo_table")
@Data
//<<< DDD / Aggregate Root
public class FuneralInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long funeralInfoId;
    private Long customerId;
    private String validationStatus;
    private String deceasedName;
    private String deceasedNameHanja;
    private String deceasedRrn;
    private Integer deceasedAge;
    private Date deceasedBirthOfDate;
    private String deceasedGender;
    private Date deceasedDate;
    private String deceasedReligion;
    private String deceasedRegisteredAddress;
    private String deceasedAddress;
    private String deceasedRelationToHouseholdHead;
    private Date reportRegistrationDate;
    private String deathLocation;
    private String deathLocationType;
    private String deathLocationEtc;
    private String deathReportEtc;
    private String reporterName;
    private String reporterRrn;
    private String reporterQualification;
    private String reporterRelationToDeceased;
    private String reporterAddress;
    private String reporterPhone;
    private String reporterEmail;
    private String submitterName;
    private String submitterRrn;
    private String funeralCompanyName;
    private String directorName;
    private String directorPhone;
    private String funeralHomeName;
    private String funeralHomeAddress;
    private String funeralHomeAddressUrl;
    private String funeralDuration;
    private String mortuaryInfo;
    private String processionDateTime;
    private String burialSiteInfo;
    private String chiefMourners;
    private String chiefMournersContact;
    private String chiefMournerAccountHolder;
    private String chiefMournerBankName;
    private String chiefMournerAccountNumber;
    private String templateKeyword;

    @PostPersist
    public void onPostPersist() {
        FuneralInfoRegistered funeralInfoRegistered = new FuneralInfoRegistered(
            this
        );
        funeralInfoRegistered.publishAfterCommit();
    }

    public static FuneralInfoRepository repository() {
        FuneralInfoRepository funeralInfoRepository = FuneralcontextApplication.applicationContext.getBean(
            FuneralInfoRepository.class
        );
        return funeralInfoRepository;
    }

    //<<< Clean Arch / Port Method
    public void updateFuneralInfo() {
        //implement business logic here:

        FuneralInfoUpdated funeralInfoUpdated = new FuneralInfoUpdated(this);
        funeralInfoUpdated.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void createObituary() {
        //implement business logic here:

        ObituaryCreationRequested obituaryCreationRequested = new ObituaryCreationRequested(
            this
        );
        obituaryCreationRequested.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void createSchedule() {
        //implement business logic here:

        ScheduleCreationRequested scheduleCreationRequested = new ScheduleCreationRequested(
            this
        );
        scheduleCreationRequested.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void createAllDocuments() {
        //implement business logic here:

        AllDocumentsCreationRequested allDocumentsCreationRequested = new AllDocumentsCreationRequested(
            this
        );
        allDocumentsCreationRequested.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void createDeathReport() {
        //implement business logic here:

        DeathReportCreationRequested deathReportCreationRequested = new DeathReportCreationRequested(
            this
        );
        deathReportCreationRequested.publishAfterCommit();
    }
    
    // --- [2. Controller가 호출할 메서드 수정] ---
    public void validateFuneralInfo() {
        System.out.println("🤖 FuneralInfo Aggregate에서 AI 검증을 시작합니다...");
        
        FuneralInfoAiValidator validator = FuneralcontextApplication.applicationContext.getBean(
            FuneralInfoAiValidator.class
        );

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            // [주석] FuneralInfo 객체를 Map으로 변환합니다. (이 부분은 나중에 전체 객체 검증으로 바뀔 수 있습니다.)
            Map<String, Object> dataAsMap = objectMapper.convertValue(this, Map.class);
            
            // [수정] Validator가 List<AiValidationError>를 반환하므로, 
            //       이 리스트가 비어있는지(isEmpty) 여부로 isDataValid를 결정합니다.
            boolean isDataValid = validator.validateData(dataAsMap).isEmpty();

            System.out.println("🤖 AI 검증 결과: " + (isDataValid ? "정상 (OK)" : "오류 (ERROR)"));
            this.processValidationResult(isDataValid);

        } catch (Exception e) {
            System.out.println("❌ 데이터 변환 중 오류 발생: " + e.getMessage());
            this.processValidationResult(false);
        }
    }

    // --- [3. 검증 결과 처리 메서드 추가] ---
    // [주석] AI 검증 결과를 받아 상태를 변경하고, 성공 시에만 이벤트를 발행하는 내부 로직입니다.
    public void processValidationResult(boolean isDataValid) {
        if (isDataValid) {
            this.setValidationStatus("VALIDATED");
            
            // 상태가 'VALIDATED'일 때만 이벤트를 발행합니다.
            FuneralInfoValidated funeralInfoValidated = new FuneralInfoValidated(this);
            funeralInfoValidated.publishAfterCommit();

        } else {
            this.setValidationStatus("ERROR");
        }
    }
}
//>>> DDD / Aggregate Root
