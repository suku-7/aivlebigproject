package aivlebigproject.domain;

import aivlebigproject.FuneralpythonApplication;
import aivlebigproject.domain.DeathReportDocumentGenerated;
import aivlebigproject.domain.ObituaryDocumentGenerated;
import aivlebigproject.domain.ScheduleDocumentGenerated;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Python_table")
@Data
//<<< DDD / Aggregate Root
public class Python {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @PostPersist
    public void onPostPersist() {
        ObituaryDocumentGenerated obituaryDocumentGenerated = new ObituaryDocumentGenerated(
            this
        );
        obituaryDocumentGenerated.publishAfterCommit();

        DeathReportDocumentGenerated deathReportDocumentGenerated = new DeathReportDocumentGenerated(
            this
        );
        deathReportDocumentGenerated.publishAfterCommit();

        ScheduleDocumentGenerated scheduleDocumentGenerated = new ScheduleDocumentGenerated(
            this
        );
        scheduleDocumentGenerated.publishAfterCommit();
    }

    public static PythonRepository repository() {
        PythonRepository pythonRepository = FuneralpythonApplication.applicationContext.getBean(
            PythonRepository.class
        );
        return pythonRepository;
    }
}
//>>> DDD / Aggregate Root
