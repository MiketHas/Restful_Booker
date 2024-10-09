import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.restful.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class GetBookingIdsTests extends BaseTest {

    @Test
    public void getAllBookingIdsWithoutFilterTest() {
        Response response = RestAssured.given(spec).get("/booking");
        // Verify response 200
        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 but it's not!");

        // Get the list of all booking Ids and verify that the list is not empty
        List<Integer> bookingIds = response.jsonPath().getList("bookingid");
        Assert.assertTrue(!bookingIds.isEmpty(), "Booking Ids is an empty list and it shouldn't be!");
    }

    @Test
    public void getBookingIdWithFilterTest() {
        // Create a specific booking to be searched later:
        Response response = createBooking(); // Ben Kenobi 1077
        response.prettyPrint();

        SoftAssert softAssert = new SoftAssert();

        spec.queryParam("firstname", firstNameCreated); // first parameter added
        spec.queryParam("lastname", lastNameCreated); // second parameter added
        Response newResponse = RestAssured.given(spec).get("/booking"); // filters the list of bookingIds
        newResponse.print(); // prints the list of bookingIds that match the queryParam criteria

        List<Integer> filteredBookings = newResponse.jsonPath().getList("bookingid");
        System.out.println("There are " + filteredBookings.size() + " results matching the filter.");
        softAssert.assertTrue(!filteredBookings.isEmpty(), "There are no bookings matching the filter!");

        // Checking if the filtered results actually match the query
        for (Integer filteredBooking : filteredBookings) {
            response = RestAssured.given(spec).get("/booking/" + filteredBooking);
            String responseFirstName = response.jsonPath().getString("firstname");
            softAssert.assertEquals(responseFirstName, firstNameCreated, "The first name doesn't match!");
            String responseLastName = response.jsonPath().getString("lastname");
            softAssert.assertEquals(responseLastName, lastNameCreated, "The last name doesn't match!");
            softAssert.assertAll();
        }
    }
}
