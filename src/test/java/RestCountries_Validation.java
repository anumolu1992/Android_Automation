import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class RestCountries_Validation {

    /*Assert response code equals to success (200) */
    @Test
    void test_01(){
        Response response = RestAssured.get("https://restcountries.com/v3.1/alpha/per");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    /*Assert country capital contains expected value */
    @Test
    void test_02(){
        Response response = RestAssured.get("https://restcountries.com/v3.1/alpha/per");
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains("Lima"), true);
    }

    /*Assert response code not equals to 400 */
    @Test
    void test_03(){
        Response response = RestAssured.get("https://restcountries.com/v3.1/capital/lima");
        int statusCode = response.getStatusCode();
        Assert.assertNotEquals(statusCode, 400);
    }

    /*Assert response code equals to 404 with invalid path */
    @Test
    void test_04(){
        Response response = RestAssured.get("https://restcountries.com/v3.1/capital/76353839");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 404);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains("\"PE\""), false);
    }


    /*Assert response code equals to 404 with invalid path and expected data not present in response */
    @Test
    void test_05(){
        Response response = RestAssured.get("https://restcountries.com/v3.1/capital/76353839");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 404);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertFalse(bodyAsString.contains("\"PE\""), "Response body contains \"PE\"");
    }

    /*Assert response code equals to 200 with country code and expected data present in response */
    @Test
    void test_06(){
        Response response = RestAssured.get("https://restcountries.com/v3.1/alpha?codes=col,pe,at");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertTrue(bodyAsString.contains("\"cca2\""), "Response body contains \"cca2\"");
    }

    /*Assert country capital in response matches expected value */
    @Test
    void test_07(){
        Response response = RestAssured.get("https://restcountries.com/v3.1/alpha/per");
         List output = response.jsonPath().getList("capital[0]");
        Assert.assertEquals(output.get(0), "Lima");
    }

    /* Log time needed to fetch the response from the backend */
    @Test
    public static void getResponseTime(){
        Response response = RestAssured.get("https://restcountries.com/v3.1/alpha/per");
        System.out.println("The time taken to fetch the response is "+ response.timeIn(TimeUnit.MILLISECONDS) + " milliseconds");
    }

}
