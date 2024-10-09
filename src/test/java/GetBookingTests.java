import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.restful.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class GetBookingTests extends BaseTest {

    @Test
    public void getBookingTest() {
        // Creating a booking
        Response response = createBooking(); // Ben Kenobi 1077

        // Set path parameter
        spec.pathParam("bookingId", response.jsonPath().getInt("bookingid"));

        // Get Response with booking
        Response bookingResponse = RestAssured.given(spec).get("/booking/{bookingId}");
        //bookingResponse.prettyPrint(); (commented so that it doesn't pollute the test results when ran from .xml

        // Assert response 200
        Assert.assertEquals(bookingResponse.getStatusCode(), 200, "Response should be 200!");
    }
}
