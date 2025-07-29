package aivlebigproject.domain;

import aivlebigproject.domain.*;
import aivlebigproject.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class DeathReportDataCreated extends AbstractEvent {

    private Long id;

    public DeathReportDataCreated(DeathReport aggregate) {
        super(aggregate);
    }

    public DeathReportDataCreated() {
        super();
    }
}
//>>> DDD / Domain Event
