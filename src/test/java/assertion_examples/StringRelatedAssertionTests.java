package assertion_examples;

import io.qameta.allure.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@Epic("Rest Assured POC - Example Tests")
@Feature("Performing different API Tests using Rest-Assured")
@Story("String related Assertions using Hamcrest in rest assured")
public class StringRelatedAssertionTests {

    private static RequestSpecBuilder requestSpecBuilder;
    private static ResponseSpecBuilder responseSpecBuilder;
    private static RequestSpecification requestSpecification;
    private static ResponseSpecification responseSpecification;

    @BeforeClass
    public void setupSpecBuilder(){

        requestSpecBuilder = new RequestSpecBuilder().setBaseUri("https://reqres.in/api/users/")
                .addQueryParam("page", 2)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter());

        responseSpecBuilder = new ResponseSpecBuilder().expectStatusCode(200)
                .expectBody("page", equalTo(2));

        responseSpecification = responseSpecBuilder.build();
        requestSpecification = requestSpecBuilder.build();
    }

    @Test
    @Description("Example Test for performing String related assertions using Hamcrest")
    @Severity(SeverityLevel.NORMAL)
    public void testStringAssertions(){
        given().spec(requestSpecification)
                .get()
                .then()
                .spec(responseSpecification)
                .assertThat()
                .body("data[0].first_name", equalTo("Michael"))
                .body("data[0].first_name", equalToIgnoringCase("MICHael"))
                .body("data[0].email", containsString("michael.lawson"))
                .body("data[0].last_name", startsWith("L"))
                .body("data[0].last_name", endsWithIgnoringCase("N"))
                .body("data[1].first_name", equalToCompressingWhiteSpace("   Lindsay"));
    }
    @Test
    @Description ("Example Test for performing Not null assertions using Hamcrest")
    @Severity (SeverityLevel.NORMAL)
    public void testHasKeyAssertion(){
        given().spec(requestSpecification)
                .get()
                .then()
                .spec(responseSpecification)
                .assertThat()
                .body("data[0]", hasKey("email"))
                .body("support", hasKey("url"))
                .body("$", hasKey("page"))
                .body("$", hasKey("total"));
    }
    @Test
    @Description ("Example Test for performing Not assertions using Hamcrest")
    @Severity (SeverityLevel.NORMAL)
    public void testNotAssertions(){
        given().spec(requestSpecification)
                .get()
                .then()
                .spec(responseSpecification)
                .assertThat()
                .body("data", not(emptyArray()))
                .body("data[0].first_name", not(equalTo("George")))
                .body("data.size()", greaterThan(5));
    }
    @Test
    @Description ("Example Test for multiple assertions in single statement using Hamcrest")
    @Severity (SeverityLevel.NORMAL)
    public void testMultipleAssertStatement () {
        given().spec(requestSpecification)
                .get()
                .then()
                .spec(responseSpecification)
                .assertThat()
                .body("page", equalTo(2), "data[0].first_name", equalTo("Michael"), "support.url", is(notNullValue()));
    }

    @AfterMethod
    public void getTestExecutionTime(ITestResult result){
        String methodName = result.getMethod().getMethodName();
        long totalExecutionTime = result.getEndMillis() - result.getStartMillis();
        System.out.println("Total execution time: "+ totalExecutionTime+" milliseconds"+" for method "+methodName );
    }





}
