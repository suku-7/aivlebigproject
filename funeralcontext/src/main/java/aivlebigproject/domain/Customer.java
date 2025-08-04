// ========================================
// FILENAME: aivlebigproject/funeralcontext/src/main/java/aivlebigproject/domain/Customer.java
// 역할 : 
// ========================================

package aivlebigproject.domain;

import aivlebigproject.FuneralcontextApplication;
import aivlebigproject.domain.CustomerRegistered;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Customer_table")
@Data
//<<< DDD / Aggregate Root
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @PostPersist
    public void onPostPersist() {
        CustomerRegistered customerRegistered = new CustomerRegistered(this);
        customerRegistered.publishAfterCommit();
    }

    public static CustomerRepository repository() {
        CustomerRepository customerRepository = FuneralcontextApplication.applicationContext.getBean(
            CustomerRepository.class
        );
        return customerRepository;
    }
}
//>>> DDD / Aggregate Root
