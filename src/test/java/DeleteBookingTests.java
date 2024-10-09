import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.restful.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class DeleteBookingTests extends BaseTest {

    @Test
    public void deleteBookingTest() {
        // Create booking
        Response response = createBooking();

        // Get bookingId
        int bookingId = response.jsonPath().getInt("bookingid");

        // Delete booking
        Response deleteResponse = RestAssured.given(spec).auth()
                .preemptive()
                .basic("admin", "password123")
                .delete("/booking/" + bookingId);
        deleteResponse.print(); // Will print "Created" instead of "Deleted".

        // Verification
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(deleteResponse.getStatusCode(), 201, "Response should be a 200!");
        // Checking if the booking still exists:
        Response responseCheck = RestAssured.given(spec).get("/booking/" + bookingId);
        responseCheck.print();
        softAssert.assertEquals(responseCheck.getBody().asString(), "Not Found", "Booking " + bookingId + " should be deleted but wasn't!");
        softAssert.assertAll();
    }
}
