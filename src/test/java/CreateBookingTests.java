import io.restassured.response.Response;
import org.restful.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class CreateBookingTests extends BaseTest {

    @Test
    public void createBookingTest() {
        // Creating a booking
        Response response = createBooking(); // Ben Kenobi 1077

        // Assert response 200
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(response.getStatusCode(), 200, "Response should be 200!");

        // Verify all fields
        String firstName = response.jsonPath().getString("booking.firstname");
        softAssert.assertEquals(firstName, firstNameCreated, "Wrong name!");

        String lastName = response.jsonPath().getString("booking.lastname");
        softAssert.assertEquals(lastName, lastNameCreated, "Wrong surname!");

        int price = response.jsonPath().getInt("booking.totalprice");
        softAssert.assertEquals(price, totalPriceCreated, "Wrong price!");

        boolean depositPaid = response.jsonPath().getBoolean("booking.depositpaid");
        softAssert.assertEquals(depositPaid, depositPaidCreated, "DepositPaid should be " + depositPaidCreated);

        String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
        softAssert.assertEquals(actualCheckin, checkInCreated, "Wrong checkin date!");

        String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
        softAssert.assertEquals(actualCheckout, checkOutCreated, "Wrong checkout date!");

        String actualNeeds = response.jsonPath().getString("booking.additionalneeds");
        softAssert.assertEquals(actualNeeds, additionalNeedsCreated, "Additional needs do not match!");

        softAssert.assertAll();
    }
}
