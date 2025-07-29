package aivlebigproject.domain;

import aivlebigproject.domain.*;
import aivlebigproject.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ScheduleDataCreated extends AbstractEvent {

    private Long id;

    public ScheduleDataCreated(Schedule aggregate) {
        super(aggregate);
    }

    public ScheduleDataCreated() {
        super();
    }
}
//>>> DDD / Domain Event
