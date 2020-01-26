package telenor;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
    @Test
    public void testGreetingWithIdSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:5000/greeting?account=personal&id=123";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        // Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains("Hi, userId 123"));
    }

    @Test
    public void testGreetingWithAccountAndTypeSuccess() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:5000/greeting?account=business&type=big";
        URI uri = new URI(baseUrl);

        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);

        // Verify request succeed
        Assert.assertEquals(200, result.getStatusCodeValue());
        Assert.assertEquals(true, result.getBody().contains("Welcome, business user!"));
    }

    @Test
    public void testGreetingWithWrongId() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:5000/greeting?account=personal&id=0";
        URI uri = new URI(baseUrl);

        try {
            restTemplate.getForEntity(uri, String.class);
            Assert.fail();
        } catch (HttpClientErrorException ex) {
            // Verify bad request and error message
            Assert.assertEquals(400, ex.getRawStatusCode());
            Assert.assertEquals(true, ex.getResponseBodyAsString().contains("id must be a positive integer"));
        }
    }

    @Test
    public void testGreetingWithWrongAccount() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:5000/greeting?account=testtest";
        URI uri = new URI(baseUrl);

        try {
            restTemplate.getForEntity(uri, String.class);
            Assert.fail();
        } catch (HttpClientErrorException ex) {
            // Verify bad request and error message
            Assert.assertEquals(400, ex.getRawStatusCode());
            Assert.assertEquals(true, ex.getResponseBodyAsString().contains("This account is not valid"));
        }
    }

    @Test
    public void testGreetingWithWrongType() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:5000/greeting?account=business&type=medium";
        URI uri = new URI(baseUrl);

        try {
            restTemplate.getForEntity(uri, String.class);
            Assert.fail();
        } catch (HttpClientErrorException ex) {
            // Verify bad request and wrong type
            Assert.assertEquals(400, ex.getRawStatusCode());
            Assert.assertEquals(true, ex.getResponseBodyAsString().contains("This type is not valid"));
        }
    }

    @Test
    public void testGreetingUserWithWrongAccountAndTypeCombination() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:5000/greeting?account=business&type=small";
        URI uri = new URI(baseUrl);

        try {
            restTemplate.getForEntity(uri, String.class);
            Assert.fail();
        } catch (HttpClientErrorException ex) {
            // Verify bad request and error message
            Assert.assertEquals(400, ex.getRawStatusCode());
            Assert.assertEquals(true, ex.getResponseBodyAsString().contains("The path is not yet implemented"));
        }
    }

    @Test
    public void testGreetingWithoutAnyParameter() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:5000/greeting";
        URI uri = new URI(baseUrl);

        try {
            restTemplate.getForEntity(uri, String.class);
            Assert.fail();
        } catch (HttpClientErrorException ex) {
            // Verify bad request and error message
            Assert.assertEquals(400, ex.getRawStatusCode());
            Assert.assertEquals(true, ex.getResponseBodyAsString().contains("No input provided"));
        }
    }
}
