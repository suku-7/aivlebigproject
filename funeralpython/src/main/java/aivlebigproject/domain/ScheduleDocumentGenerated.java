package aivlebigproject.domain;

import aivlebigproject.domain.*;
import aivlebigproject.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ScheduleDocumentGenerated extends AbstractEvent {

    private Long id;

    public ScheduleDocumentGenerated(Python aggregate) {
        super(aggregate);
    }

    public ScheduleDocumentGenerated() {
        super();
    }
}
//>>> DDD / Domain Event
