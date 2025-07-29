package aivlebigproject.domain;

import aivlebigproject.domain.*;
import aivlebigproject.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class ObituaryDataCreated extends AbstractEvent {

    private Long id;

    public ObituaryDataCreated(Obituary aggregate) {
        super(aggregate);
    }

    public ObituaryDataCreated() {
        super();
    }
}
//>>> DDD / Domain Event
