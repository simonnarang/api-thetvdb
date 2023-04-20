package com.omertron.thetvdbapi.model;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.*;
public class RestApiTest {
    String headerVal = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZ2UiOiIiLCJhcGlrZXkiOiIzODdmMDBhZi01OWVmLTRlY2UtODYzNy1iZmEwYjA1NTMyY2IiLCJjb21tdW5pdHlfc3VwcG9ydGVkIjpmYWxzZSwiZXhwIjoxNjg0NTU0ODgzLCJnZW5kZXIiOiIiLCJoaXRzX3Blcl9kYXkiOjEwMDAwMDAwMCwiaGl0c19wZXJfbW9udGgiOjEwMDAwMDAwMCwiaWQiOiIyNDIwNjQxIiwiaXNfbW9kIjpmYWxzZSwiaXNfc3lzdGVtX2tleSI6ZmFsc2UsImlzX3RydXN0ZWQiOmZhbHNlLCJwaW4iOm51bGwsInJvbGVzIjpbXSwidGVuYW50IjoidHZkYiIsInV1aWQiOiIifQ.S13Ig6uFvtuwBwWrjWQjqCCVuXsNDZDzrDyLGQTMcYnnznUp0NQZmI8vJrcHuELUFdW-i2lSPWEQ3vt9aGIx7w2O5ElHGr0LubSSynr-F0JkCIYt9rB2_RX_L_YDEn4sIpZUJXdALp-HzhCAnfzrFSPPPOHurJYXRHlNa5mxz6u-Qi0DI5iEzcmrHIIA7Q3N30Q4KqILi1k9l_rEQYWdLfzfr6iv0AJzhgQLPHbAK1GYrEJUZkYRaJE0xrt8ub32fx-JQVOkPZQbkxo0y_EOcrglBsXgke1rv_WyTggBuppgCS1TyshaUPVFkV_2PniABF4MyjAXdnPgj3RHZKA0SxhaxyhRTsUE3TpV7nSMkYoEu7T5-AcgM7259d4bjpGqEIA6xYyEuyhBSjATknFdDhBz5ar_3txVbEMOJJ1MyFZ-GYz-YOeWc5QOv9G8uOjJfdVnk-VGTqIJKYIbAdg8oz6SiMqQVky-4LaDAv4it8KdgzOOZ397ABmWEZjm-2rTkUwBAD24yf7zThnK7y35YxxlFRc9Yules59LnBxMyJKeZN1nuV-xPy5VukzA95cJ7Q3eOeQeSorNtjJFCRlBVHD0h9hHpu5uCQHPverKqKSkE97UXcIA9wVI29JJtjuiJ0-IbBUXnMWHPEG8_SQLqW1vZXSbJcsk72aXZaWydeY";

    /** TESTING ARTWORK ENDPOINTS **/
    @Test
    //Tests successfully retrieves artwork by a valid ID
    public void testGetArtworkByValidID() {
        given().
                header("Authorization", headerVal).
                when().
                get("https://api4.thetvdb.com/v4/artwork/63402375").
                then().
                assertThat().statusCode(200).body("data.id",equalTo(63402375));
    }

    @Test
    //Tests that attempting to retrieve artwork with an ID that doesn't exist fails
    public void testGetArtworkBadIDFails() {
        given().
                header("Authorization", headerVal).
                when().
                get("https://api4.thetvdb.com/v4/artwork/02").
                then().
                assertThat().statusCode(404).body("data",equalTo(null));
    }

    @Test
    //Test that attempting to retrivew artwork with an improperly formatted ID fails
    public void testGetArtworkInvalidID() {
        given().
                header("Authorization", headerVal).
                when().
                get("https://api4.thetvdb.com/v4/artwork/SAM").
                then().
                assertThat().statusCode(400);
    }

    @Test
    //Test that retrieving /artwork/id/extended gives additional fields, like 'movieID', 'thumbnailWidth', and 'thumbnailHeight'
    public void testGetArtworkIDExtendedHasExtraFields() {
        given().
                header("Authorization", headerVal).
                when().
                get("https://api4.thetvdb.com/v4/artwork/63402375/extended").
                then().
                assertThat().statusCode(200).body("data.id",equalTo(63402375)).body("data.movieId",notNullValue())
                .body("data.thumbnailWidth",notNullValue()).body("data.thumbnailHeight",notNullValue());

    }

    @Test
    //Test that /artwork/statuses has all 5 artwork statuses (Low Quality, Improper..., Spoiler, Adult Content, Auto Resized
    public void testArtworkStatusesAllThere() {
        given().
                header("Authorization", headerVal).
                when().get("https://api4.thetvdb.com/v4/artwork/statuses").
                then().
                assertThat().statusCode(200).
                body("data.size()",equalTo(5)).
                body("data[0].name",equalTo("Low Quality")).
                body("data[1].name",equalTo("Improper Action Shot")).
                body("data[2].name",equalTo("Spoiler")).
                body("data[3].name",equalTo("Adult Content")).
                body("data[4].name",equalTo("Automatically Resized"));
    }

    @Test
    //Testing /artwork/types has correct fields & 27 unique types (like banner for series and also banner for movie... 27)
    public void testArtworkTypesAllThere() {
        given().
                header("Authorization", headerVal).
                when().get("https://api4.thetvdb.com/v4/artwork/types").
                then().
                assertThat().statusCode(200).
                body("data.size()",equalTo(24)). //Note: ID's are numbered weirdly; gives impression that there should be 27 types
                body("data[0].id", notNullValue()).
                body("data[0].name", notNullValue()).
                body("data[0].recordType", notNullValue()).
                body("data[0].slug", notNullValue()).
                body("data[0].imageFormat", notNullValue()).
                body("data[0].width", notNullValue()).
                body("data[0].height", notNullValue()).
                body("data[0].thumbWidth", notNullValue()).
                body("data[0].thumbHeight", notNullValue());
    }
}
