package telenor;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Validated
public class Application {
    @RequestMapping("/greeting")
    public ResponseEntity<String> greeting(@RequestParam(value = "account", required = false) String account,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "id", required = false) Integer id) throws Exception {
        ValidationResult validationResult = validateParameters(account, type, id);
        if (validationResult.hasError) {
            return validationResult.returnValue;
        }
        if (id != null) {
            return new ResponseEntity<>("Hi, userId " + id, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Welcome, " + account + " user!", HttpStatus.OK);
        }
    }

    ValidationResult validateParameters(String account, String type, Integer id) {
        ValidationResult result = new ValidationResult();
        if (id == null && account == null && type == null) {
            result.hasError = true;
            result.returnValue = new ResponseEntity<>("No input provided", HttpStatus.BAD_REQUEST);
        } else if (!isValidId(id)) {
            result.hasError = true;
            result.returnValue = new ResponseEntity<>("id must be a positive integer", HttpStatus.BAD_REQUEST);
        } else if (!isValidAccount(account)) {
            result.hasError = true;
            result.returnValue = new ResponseEntity<>("This account is not valid", HttpStatus.BAD_REQUEST);
        } else if (!isValidType(type)) {
            result.hasError = true;
            result.returnValue = new ResponseEntity<>("This type is not valid", HttpStatus.BAD_REQUEST);
        } else if (!isValidAccountAndTypeCombination(account, type)) {
            result.hasError = true;
            result.returnValue = new ResponseEntity<>("The path is not yet implemented", HttpStatus.BAD_REQUEST);
        }        
        return result;
    }

    private boolean isValidId(Integer id) {
        return id == null || id > 0;
    }

    private boolean isValidAccount(String account) {
        List<String> accounts = Arrays.asList("personal", "business");
        return account == null || accounts.contains(account);
    }

    private boolean isValidType(String type) {
        List<String> types = Arrays.asList("big", "small");
        return type == null || types.contains(type);
    }

    private boolean isValidAccountAndTypeCombination(String account, String type) {
        return !("business".equals(account) && "small".equals(type));
    }

    class ValidationResult {
        boolean hasError = false;
        ResponseEntity<String> returnValue = new ResponseEntity<>(HttpStatus.OK);
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}