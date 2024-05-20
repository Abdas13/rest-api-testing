package assertion_examples;

import io.qameta.allure.*;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("RestAssured POC - Example Tests")
@Feature("Peerforming different API tests using Rest-Assured")
@Story("Number related Assertions using Hamcrest in Rest-Assred")
public class NumberRelatedAssertions {

    private static String URL = "https://reqres.in/api/users/";

    @Test
    @Description("Example Test for performing number related assertions using Hamcrest")
    @Severity(SeverityLevel.NORMAL)
    public void testNumberAssertions(){

        given().when()
                .queryParam("page", 2)
                .get(URL)
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("page", equalTo(2))
                .body("per_page", greaterThan(4))
                .body ("per_page", greaterThanOrEqualTo (6))
                .body ("total", lessThan (14))
                .body ("total_pages", lessThanOrEqualTo (3));
    }
    @Test
    @Description ("Example Test for performing Greater than assertions using Hamcrest")
    @Severity (SeverityLevel.NORMAL)
    public void testGreaterThanAsseretaions(){
        given().when()
                .queryParam("page", 2)
                .get(URL)
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("per_page", greaterThan(4))
                .body("per_page", greaterThanOrEqualTo(6));
    }
    @Test
    @Description ("Example Test for performing Less than assertions using Hamcrest")
    @Severity (SeverityLevel.NORMAL)
    public void testLessThanAssertions (){
        given().when()
                .queryParam("page", 2)
                .get(URL)
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("per_page", lessThan(14))
                .body("per_page", lessThanOrEqualTo(7));
    }
    @AfterMethod
    public void getTestExecutionTime(ITestResult result){
        String methodName = result.getMethod().getMethodName();
        long totalExecutionTime = (result.getEndMillis() - result.getStartMillis());
        System.out.println("Total execution time: "+totalExecutionTime+" milliseconds" +" for method "+methodName);
    }

}
