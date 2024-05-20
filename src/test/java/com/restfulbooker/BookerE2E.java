package com.restfulbooker;

import data.restfulbooker.BookingData;

import static data.restfulbooker.BookingDataBuilder.getPartialBookingData;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.*;

import data.restfulbooker.PartialBookingData;
import io.qameta.allure.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.internal.ResponseSpecificationImpl;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static data.restfulbooker.BookingDataBuilder.getBookingData;
import static io.restassured.RestAssured.given;

@Epic("Rest Assured POC - Example Tests")
@Feature("Writing End to End tests using rest-assured")
public class BookerE2E extends BaseSetup{
    private BookingData newBooking;
    private int bookingId;
    private String token;

    @BeforeTest
    public void testSetup(){
        newBooking = getBookingData();
    }
    @Test()
    @Description("Example test for creating new booking - Post request")
    @Severity(SeverityLevel.BLOCKER)
    @Story("End to End tests using rest-assured")
    @Step("Create new booking")
    public void createBookingTest(){
        bookingId = given().body(newBooking)
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("bookingid", notNullValue())
                .body("booking.firstname", equalTo(newBooking.getFirstname()), "booking.lastname", equalTo(newBooking.getLastname()),
                        "booking.totalprice", equalTo(newBooking.getTotalprice()),
                        "booking.depositpaid", equalTo(newBooking.isDepositpaid()), "booking.bookingdates.checkin", equalTo(
                                newBooking.getBookingdates()
                                        .getCheckin()), "booking.bookingdates.checkout", equalTo(newBooking.getBookingdates()
                                .getCheckout()), "booking.additionalneeds", equalTo(newBooking.getAdditionalneeds()))
                .extract()
                .path("bookingid");

    }
    @Test(dependsOnMethods = "createBookingTest")
    @Description("Example test for retrieving a booking - Get request")
    @Severity(SeverityLevel.CRITICAL)
    @Story("End to End tests using rest-assured")
    @Step("Get a the newly created booking")
    public void getBookingTest(){
        given().get("/booking/"+bookingId)
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("firstname", equalTo(newBooking.getFirstname()), "lastname", equalTo(newBooking.getLastname()),
                        "totalprice", equalTo(newBooking.getTotalprice()), "depositpaid",
                        equalTo(newBooking.isDepositpaid()), "bookingdates.checkin", equalTo(newBooking.getBookingdates()
                                .getCheckin()), "bookingdates.checkout", equalTo(newBooking.getBookingdates()
                                .getCheckout()), "additionalneeds", equalTo(newBooking.getAdditionalneeds()));

    }
    @Test(dependsOnMethods = "createBookingTest")
    @Description("Example test for updating a booking - Put request")
    @Severity(SeverityLevel.NORMAL)
    @Story("End to End tests using rest-assured")
    @Step("Update the booking")
    public void updateBookingTest() {
        BookingData updatedBooking = getBookingData();
        given().body(updatedBooking)
                .when()
                .header("Cookie", "token=" + token)
                .put("/booking/" + bookingId)
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("firstname", equalTo(updatedBooking.getFirstname()), "lastname",
                        equalTo(updatedBooking.getLastname()), "totalprice", equalTo(updatedBooking.getTotalprice()),
                        "depositpaid", equalTo(updatedBooking.isDepositpaid()), "bookingdates.checkin", equalTo(
                                updatedBooking.getBookingdates()
                                        .getCheckin()), "bookingdates.checkout", equalTo(updatedBooking.getBookingdates()
                                .getCheckout()), "additionalneeds", equalTo(updatedBooking.getAdditionalneeds()));
    }
    @Test(dependsOnMethods = "createBookingTest")
    @Description("Example test for updating a booking partially- Patch request")
    @Severity(SeverityLevel.NORMAL)
    @Story("End to End tests using rest-assured")
    @Step("Update the booking partially")
    public void updatePartialBookingTest(){
        PartialBookingData partialBookingData = getPartialBookingData();
        given().body(partialBookingData)
                .when()
                .header("Cookie", "token="+token)
                .patch("/booking/"+bookingId)
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("firstname", equalTo(partialBookingData.getFirstname()), "totalprice", partialBookingData.getTotalprice());
    }
    @Test
    public void runTest(){
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("http://localhost:3001").build();
        ResponseSpecification rep = new ResponseSpecBuilder().build();

        given().when().get("/booking/2")
                .then()
                .statusCode(200)
                .and()
                .assertThat()
                .body("firstname", containsString("i"));

    }

}
