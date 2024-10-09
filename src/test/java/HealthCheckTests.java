import org.restful.BaseTest;
import org.testng.annotations.Test;

import java.security.BasicPermission;

import static io.restassured.RestAssured.given;

public class HealthCheckTests extends BaseTest {

    @Test
    public void healthCheckTest() {
        given()
                .spec(spec)
                .get("/ping")
                .then()
                .assertThat()
                .statusCode(201);
    }



}
