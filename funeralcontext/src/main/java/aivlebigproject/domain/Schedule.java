// ========================================
// FILENAME: aivlebigproject/funeralcontext/src/main/java/aivlebigproject/domain/Schedule.java
// 역할 : 
// ========================================

package aivlebigproject.domain;

import aivlebigproject.FuneralcontextApplication;
import aivlebigproject.domain.ScheduleDataCreated;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Schedule_table")
@Data
//<<< DDD / Aggregate Root
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;
    private Long funeralInfoId;
    private Long scheduleTemplateId;
    private String scheduleDallePrompt;
    private String scheduleDalleTemplateImageUrl;
    private String scheduleFileName;
    private String scheduleFileUrl;
    private String scheduleStatus;
    private Date scheduleCreatedAt;

    private Long customerId;
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

    public static ScheduleRepository repository() {
        ScheduleRepository scheduleRepository = FuneralcontextApplication.applicationContext.getBean(
            ScheduleRepository.class
        );
        return scheduleRepository;
    }

    //<<< Clean Arch / Port Method
    public static void updateScheduleData(
        ScheduleDocumentGenerated scheduleDocumentGenerated
    ) {
        // 1. 이벤트로 받은 scheduleId를 사용하여 DB에서 해당 Schedule 데이터를 찾습니다.
        repository().findById(scheduleDocumentGenerated.getScheduleId()).ifPresent(schedule->{
            
            // 2. 찾은 데이터의 필드 값을 이벤트로 받은 정보로 업데이트합니다.
            schedule.setScheduleFileName(scheduleDocumentGenerated.getScheduleFileName()); // [수정] FilePath -> FileName
            schedule.setScheduleFileUrl(scheduleDocumentGenerated.getScheduleFileUrl());
            schedule.setScheduleStatus(scheduleDocumentGenerated.getScheduleStatus());
            
            // [추가] DALL-E 관련 정보 업데이트
            schedule.setScheduleDallePrompt(scheduleDocumentGenerated.getScheduleDallePrompt());
            schedule.setScheduleDalleTemplateImageUrl(scheduleDocumentGenerated.getScheduleDalleTemplateImageUrl());
            
            // 3. 변경된 내용을 DB에 다시 저장합니다.
            repository().save(schedule);

        });
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
