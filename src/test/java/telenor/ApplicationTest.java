package telenor;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApplicationTest {
    @Test
    public void testValidateIdSuccess() {
        Application application = new Application();
        Application.ValidationResult result = application.validateParameters("personal", null, 123);

        Assert.assertEquals(false, result.hasError);
    }
    
    @Test
    public void testValidateAccountAndTypeSuccess() {
        Application application = new Application();
        Application.ValidationResult result = application.validateParameters("business", "big", null);

        Assert.assertEquals(false, result.hasError);
    }

    @Test
    public void testValidateIdFailure() {
        Application application = new Application();
        Application.ValidationResult result = application.validateParameters("personal", null, 0);

        Assert.assertEquals(true, result.hasError);
        Assert.assertEquals(new ResponseEntity<>("id must be a positive integer", HttpStatus.BAD_REQUEST), result.returnValue);
    }

    @Test
    public void testValidateAccountFailure() {
        Application application = new Application();
        Application.ValidationResult result = application.validateParameters("testAccount", null, null);

        Assert.assertEquals(true, result.hasError);
        Assert.assertEquals(new ResponseEntity<>("This account is not valid", HttpStatus.BAD_REQUEST), result.returnValue);
    }

    @Test
    public void testValidateTypeFailure() {
        Application application = new Application();
        Application.ValidationResult result = application.validateParameters("business", "testType", null);

        Assert.assertEquals(true, result.hasError);
        Assert.assertEquals(new ResponseEntity<>("This type is not valid", HttpStatus.BAD_REQUEST), result.returnValue);
    }

    @Test
    public void testValidateAccountAndTypeCombinationFailure() {
        Application application = new Application();
        Application.ValidationResult result = application.validateParameters("business", "small", null);

        Assert.assertEquals(true, result.hasError);
        Assert.assertEquals(new ResponseEntity<>("The path is not yet implemented", HttpStatus.BAD_REQUEST), result.returnValue);
    }

    @Test
    public void testValidateNoParameterFailure() {
        Application application = new Application();
        Application.ValidationResult result = application.validateParameters(null, null, null);

        Assert.assertEquals(true, result.hasError);
        Assert.assertEquals(new ResponseEntity<>("No input provided", HttpStatus.BAD_REQUEST), result.returnValue);
    }
}