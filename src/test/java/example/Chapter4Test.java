package example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


class Chapter4Test {
    private static RequestSpecification requestSpec;
    private static ResponseSpecification responseSpec;

    @BeforeAll
    public static void setUp() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("http://zippopotam.us")
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();

    }

    @Test
    public void requestUsZipCode90210_CheckRequestsBody_expectBeverlyHills() {
        given()
                .spec(requestSpec)
                .when()
                .get("us/90210")
                .then()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_expectBeverlyHills() {
        given()
                .spec(requestSpec)
                .when()
                .get("us/90210")
                .then()
                .spec(responseSpec)
                .and()
                .assertThat()
                .body("places[0].'place name'", equalTo("Beverly Hills"));
    }

    @Test
    public void requestUsZipCode90210_checkPlaceNameInResponseBody_assertBeverlyHills() {
        String placeName =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("us/90210")
                        .then()
                        .extract()
                        .path("places[0].'place name'");
        Assertions.assertEquals("Beverly Hills", placeName);

    }

}
