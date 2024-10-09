package org.restful;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    protected static RequestSpecification spec;
    protected static String firstNameCreated = "Ben";
    protected static String lastNameCreated = "Kenobi";
    protected static int totalPriceCreated = 1077;
    protected static boolean depositPaidCreated = true;
    protected static String checkInCreated = "2049-01-01";
    protected static String checkOutCreated = "2077-12-12";
    protected static String additionalNeedsCreated = "garlic!";


    @BeforeClass
    public void setup() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://restful-booker.herokuapp.com")
                .build();
    }

    protected static Response createBooking() {
        JSONObject body = new JSONObject();
        body.put("firstname", firstNameCreated);
        body.put("lastname", lastNameCreated);
        body.put("totalprice", totalPriceCreated);
        body.put("depositpaid", depositPaidCreated);

        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", checkInCreated);
        bookingDates.put("checkout", checkOutCreated);

        body.put("bookingdates", bookingDates);

        body.put("additionalneeds", additionalNeedsCreated);


        // Get response
        Response response = RestAssured.given(spec)
                .contentType(ContentType.JSON)
                .body(body.toString())
                .post("/booking");
        return response;
    }
}