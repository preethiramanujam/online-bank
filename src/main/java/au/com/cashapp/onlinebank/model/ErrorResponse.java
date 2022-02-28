package au.com.cashapp.onlinebank.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Data
public class ErrorResponse {

    private String errorCode;
    private String errorMessage;
    private String errorDescription;
    private HttpStatus status;
}
