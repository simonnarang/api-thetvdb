package com.omertron.thetvdbapi.model;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;

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
    //Test that when calling this endpoint without proper authentication header isn't allowed
    public void testArtworkUnauthorized() {
                when().
                get("https://api4.thetvdb.com/v4/artwork/02").
                then().
                assertThat().statusCode(401);
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

    /** TESTING MOVIE ENDPOINTS **/

    //TESTING /movies

    @Test
    //Tests that retrieving movies without proper header is unauthorized
    public void testMoviesUnauthorized() {
        when().
                get("https://api4.thetvdb.com/v4/movies").
                then().
                assertThat().statusCode(401);
    }

    @Test
    //Test retrieving first page of movies, movies have proper schema/structure
    public void testGetMoviesTheyHaveProperSchema() {
        //EACH PAGE CONTAINS A MASSIVE NUMBER OF MOVIES
        given().
                header("Authorization", headerVal).
                when().get("https://api4.thetvdb.com/v4/movies?page=1").
                then().
                assertThat().statusCode(200).
                body("data[0].aliases",instanceOf(List.class)).
                body("data[0].id",instanceOf(Integer.class)).
                body("data[0].image",instanceOf(String.class)).
                body("data[0].lastUpdated",instanceOf(String.class)).
                body("data[0].name",instanceOf(String.class)).
                body("data[0].nameTranslations",instanceOf(List.class)).
                body("data[0].overviewTranslations",instanceOf(List.class)).
                body("data[0].score",instanceOf(Integer.class)).
                body("data[0].slug",instanceOf(String.class)).
                body("data[0].status",notNullValue()).
                body("data[0].status.id",instanceOf(Integer.class)).   //GET THE FIELDS WITHIN STATUS!!!
                body("data[0].status.keepUpdated",instanceOf(Boolean.class)).
                body("data[0].status.name",instanceOf(String.class)).
                body("data[0].status.recordType",instanceOf(String.class)).
                body("data[0].runtime",instanceOf(Integer.class)).
                body("data[0].year",instanceOf(String.class));
    }

    //TESTING /movies/{id}
    @Test
    //Tests that retrieving movies without proper header is unauthorized
    public void testMoviesIDUnauthorized() {
        when().
                get("https://api4.thetvdb.com/v4/movies/344183").
                then().
                assertThat().statusCode(401);
    }

    @Test
    //Tests that using an invalid idea gives 400 error
    public void testMoviesIDInvalidID400() {
        given().
                header("Authorization", headerVal).
                when().get("https://api4.thetvdb.com/v4/movies/SAM").
                then().
                assertThat().statusCode(400);
    }

    @Test
    //Tests that using an ID that doesn't exist gives 404 not found
    public void testMoviesIDNonexistentID404() {
        given().
                header("Authorization", headerVal).
                when().get("https://api4.thetvdb.com/v4/movies/39848329432").
                then().
                assertThat().statusCode(404);
    }

    @Test
    //Tests that using an ID for a movie retrieves the correct movie
    public void testMovieValidIDCorrectMovie() {
        given().
                header("Authorization", headerVal).
                when().get("https://api4.thetvdb.com/v4/movies/344183").
                then().
                assertThat().statusCode(200).
                body("data.id",equalTo(344183)).
                body("data.name",equalTo("The Last Kingdom: Seven Kings Must Die"));
    }

    //TESTING /movies/filter

    @Test
    //Tests that accessing the endpoint without proper header is unauthorized
    public void testMovieFilterUnauthorized() {
        when().get("https://api4.thetvdb.com/v4/movies/filter?company=1&contentRating=245&country=usa&genre=3&lang=eng&year=2020").then().assertThat().statusCode(401);
    }

    @Test
    //Tests filtering properly gets a bunch of movies!
    public void testMovieFilterGoodFilteringHasResults() {
        given().
                header("Authorization", headerVal).
                when().get("https://api4.thetvdb.com/v4/movies/filter?country=usa&genre=24&lang=eng&year=2020"). //ton of movies based on this filtering
                then().
                assertThat().statusCode(200).
                body("data.size()",greaterThan(0));
    }


}
