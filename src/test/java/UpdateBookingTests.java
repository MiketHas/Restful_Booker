import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.restful.BaseTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class UpdateBookingTests extends BaseTest {

    @Test
    public void updateBookingPutTest() {
        // Creating a booking
        Response response = createBooking(); // Ben Kenobi 1077
        // Printing the new booking:
        //response.prettyPrint(); (commented so that it doesn't pollute the test results when ran from .xml

        // Get the bookingId of the created booking
        int bookingId = response.jsonPath().getInt("bookingid");

        // Creating JSON body
        JSONObject bodyUpdate = new JSONObject();
        bodyUpdate.put("firstname", "Han");
        bodyUpdate.put("lastname", "Solo");
        bodyUpdate.put("totalprice", 2049);
        bodyUpdate.put("depositpaid", false);
        JSONObject bookingDates = new JSONObject();
        bookingDates.put("checkin", "1999-01-02");
        bookingDates.put("checkout", "2000-03-04");
        bodyUpdate.put("bookingdates", bookingDates);
        bodyUpdate.put("additionalneeds", "dog food");

        // Update booking fields
        /*"username" : "admin",
        "password" : "password123"*/

        Response responseUpdate = RestAssured.given(spec).auth().preemptive()
                .basic("admin", "password123")
                .contentType(ContentType.JSON).
                body(bodyUpdate.toString()).put("/booking/" + bookingId);

        // Verifications
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseUpdate.getStatusCode(), 200, "Response should be 200!");

        // Verify all fields
        String firstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(firstName, "Han", "Wrong name!");

        String lastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(lastName, "Solo", "Wrong surname!");

        int price = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, 2049, "Wrong price!");

        boolean depositPaid = responseUpdate.jsonPath().getBoolean("depositpaid");
        softAssert.assertFalse(depositPaid, "depositPaid should be False!");

        String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, "1999-01-02", "Wrong checkin date!");

        String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, "2000-03-04", "Wrong checkout date!");

        String needs = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(needs, "dog food", "Wrong additional needs!");

        softAssert.assertAll();

        // Printing the final booking after changes:
        Response responseFinal = RestAssured.given(spec).get("/booking/" + bookingId);
        //responseFinal.prettyPrint(); (commented so that it doesn't pollute the test results when ran from .xml
    }

    @Test
    public void updateBookingPatchTest() {
        // Creating a booking
        Response response = createBooking(); // Ben Kenobi 1077

        // Printing the new booking:
        //response.prettyPrint(); (commented so that it doesn't pollute the test results when ran from .xml

        // Get the bookingId of the created booking
        int bookingId = response.jsonPath().getInt("bookingid");

        // Creating JSON body
        JSONObject bodyUpdate = new JSONObject();
        bodyUpdate.put("firstname", "Anakin");
        bodyUpdate.put("lastname", "Skywalker");
        bodyUpdate.put("additionalneeds", "Lots of sand!");

        // Update booking fields
        /*"username" : "admin",
        "password" : "password123"*/

        Response responseUpdate = RestAssured.given(spec).auth().preemptive()
                .basic("admin", "password123")
                .contentType(ContentType.JSON).
                body(bodyUpdate.toString()).patch("/booking/" + bookingId);

        // Verifications
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(responseUpdate.getStatusCode(), 200, "Response should be 200!");

        // Verify all fields
        String firstName = responseUpdate.jsonPath().getString("firstname");
        softAssert.assertEquals(firstName, "Anakin", "Wrong name!");

        String lastName = responseUpdate.jsonPath().getString("lastname");
        softAssert.assertEquals(lastName, "Skywalker", "Wrong surname!");

        int price = responseUpdate.jsonPath().getInt("totalprice");
        softAssert.assertEquals(price, totalPriceCreated, "Wrong price!");

        boolean depositPaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertEquals(depositPaid, depositPaidCreated, "DepositPaid should be " + depositPaidCreated);

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, checkInCreated, "Wrong checkin date!");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, checkOutCreated, "Wrong checkout date!");

        String needs = responseUpdate.jsonPath().getString("additionalneeds");
        softAssert.assertEquals(needs, "Lots of sand!", "Wrong additional needs!");

        softAssert.assertAll();

        // Printing the final booking after changes:
        Response responseFinal = RestAssured.given(spec).get("/booking/" + bookingId);
        //responseFinal.prettyPrint(); (commented so that it doesn't pollute the test results when ran from .xml
    }
}
