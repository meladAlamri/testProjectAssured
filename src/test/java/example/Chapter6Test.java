package example;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utility.Location;

import static io.restassured.RestAssured.*;

public class Chapter6Test {

    String url = "http://zippopotam.us/us/90210";

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills(){
        Location location =
                given()
                        .when()
                        .get(url)
                        .as(Location.class);
        Assertions.assertEquals("Beverly Hills", location.getPlaces().get(0).getPlaceName());
    }

    @Test
    public void requestUsZipCode90210_checkStatusCode_expectHttp200() {
        Location location = new Location();
        location.setCountry("Netherlands");
        given()
                .contentType(ContentType.JSON)
                .body(location)
                .log().body()
                .when()
                .post("http://zippopotam.us/lv/1050")
                .then()
                .assertThat()
                .statusCode(405);

    }
}
