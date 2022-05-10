import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RestCountries_Validation {
    String BASE_URI = "https://restcountries.com/v3.1/";
    String CITY_NAME_PATH = "alpha/";
    String CAPITAL_PATH = "capital/";
    String CITY_CODE_PATH = "?codes=";

    @DataProvider(name = "city-name-provider")
    public Object[][] dpMethodCityProvider(){
        return new Object[][] {{"per"}};
    }

    @DataProvider(name = "capital-provider")
    public Object[][] dpMethodCapitalProvider(){
        return new Object[][] {{"lima"}};
    }

    @DataProvider(name = "invalid-city-provider")
    public Object[][] dpMethodInvalidCityProvider(){
        return new Object[][] {{"76353839"}};
    }
    @DataProvider(name = "city-code-provider")
    public Object[][] dpMethodCityCodeProvider(){
        return new Object[][] {{"col","pe","at"}};
    }

    /*Assert response code equals to success (200) */
    @Test (dataProvider = "city-name-provider")
    void test_01(String cityName){
        Response response = RestAssured.get(BASE_URI+CITY_NAME_PATH+cityName);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    /*Assert country capital contains expected value */
    @Test (dataProvider = "city-name-provider")
    void test_02(String cityName){
        Response response = RestAssured.get(BASE_URI+CITY_NAME_PATH+cityName);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains("Lima"), true);
    }

    /*Assert response code not equals to 400 */
    @Test (dataProvider = "invalid-city-provider")
    void test_03(String cityName){
        Response response = RestAssured.get(BASE_URI+CAPITAL_PATH+cityName);
        int statusCode = response.getStatusCode();
        Assert.assertNotEquals(statusCode, 400);
    }

    /*Assert response code equals to 404 with invalid path */
    @Test (dataProvider = "invalid-city-provider")
    void test_04(String cityName){
        Response response = RestAssured.get(BASE_URI+CITY_NAME_PATH+CAPITAL_PATH+cityName);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 404);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertEquals(bodyAsString.contains("\"PE\""), false);
    }


    /*Assert response code equals to 404 with invalid path and expected data not present in response */
    @Test (dataProvider = "invalid-city-provider")
    void test_05(String cityName){
        Response response = RestAssured.get(BASE_URI+CAPITAL_PATH+cityName);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 404);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertFalse(bodyAsString.contains("\"PE\""), "Response body contains \"PE\"");
    }

    /*Assert response code equals to 200 with country code and expected data present in response */
    @Test (dataProvider = "city-code-provider")
    void test_06(String code1, String code2 , String code3){
        Response response = RestAssured.get(BASE_URI+CITY_NAME_PATH+CITY_CODE_PATH+code1+","+code2+","+code3);
        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();
        Assert.assertTrue(bodyAsString.contains("\"cca2\""), "Response body contains \"cca2\"");
    }

    /*Assert country capital in response matches expected value */
    @Test (dataProvider = "city-name-provider")
    void test_07(String cityName){
        Response response = RestAssured.get(BASE_URI+CITY_NAME_PATH+cityName);
         List output = response.jsonPath().getList("capital[0]");
        Assert.assertEquals(output.get(0), "Lima");
    }

    /* Log time needed to fetch the response from the backend */
    @Test (dataProvider = "city-name-provider")
    public void test_08_getResponseTime(String cityName){
        Response response = RestAssured.get(BASE_URI+CITY_NAME_PATH+cityName);
        System.out.println("The time taken to fetch the response is "+ response.timeIn(TimeUnit.MILLISECONDS) + " milliseconds");
    }

}
