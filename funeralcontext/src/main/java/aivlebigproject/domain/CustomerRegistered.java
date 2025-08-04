package aivlebigproject.domain;

import aivlebigproject.domain.*;
import aivlebigproject.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class CustomerRegistered extends AbstractEvent {

    private Long customerId;
    private String customerName;
    private Integer customerAge;
    private String customerPhone;
    private String customerJob;
    private String customerAddress;
    private String customerGender;
    private Date customerBirthOfDate;
    private Boolean customerHasChildren;
    private Boolean customerIsMarried;
    private String customerRrn;

    public CustomerRegistered(Customer aggregate) {
        super(aggregate);
    }

    public CustomerRegistered() {
        super();
    }
}
//>>> DDD / Domain Event
