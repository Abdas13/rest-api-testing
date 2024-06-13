package com.restfulbooker;


import data.restfulbooker.BookingData;
import io.qameta.allure.*;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.testng.annotations.Test;
import java.io.InputStream;
import static data.restfulbooker.BookingDataBuilder.getBookingData;
import static io.restassured.RestAssured.given;

@Epic("Rest Assured POC - Example Tests")
@Feature("JSON Schema Validation using rest-assured")
public class JsonSchemaValidationTest extends BaseSetup {

    private int bookingId;

    @Test(priority = 1)
    @Description("Example test for checking json schema for new booking - Post request")
    @Severity(SeverityLevel.CRITICAL)
    @Story("JSON Schema Validation using rest-assured")
    public void testCreateBookingJsonSchema() {

        InputStream createBookingJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("createbookingjsonschema.json");
        BookingData newBooking = getBookingData();
        bookingId = given().body(newBooking)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(createBookingJsonSchema))
                .and()
                .extract()
                .path("bookingid");
    }

    @Test(priority = 2)
    @Description("Example test for checking json schema after getting a booking - get request")
    @Severity(SeverityLevel.CRITICAL)
    @Story("JSON Schema Validation using rest-assured")
    public void testGetBookingJsonSchema() {

        InputStream getBookingJsonSchema = getClass().getClassLoader()
                .getResourceAsStream("getbookingjsonschema.json");

        given().when()
                .get("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(getBookingJsonSchema));
    }
}
