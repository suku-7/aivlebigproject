// 위치: aivlebigproject/infra/AiValidationError.java
package aivlebigproject.infra;

import lombok.Data;

@Data
public class AiValidationError {
    private String fieldName;
    private String warningDescription;
    private String suggestion;
}
