package com.omertron.thetvdbapi.model;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.*;
public class RestApiTest {
    String headerVal = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZ2UiOiIiLCJhcGlrZXkiOiIzODdmMDBhZi01OWVmLTRlY2UtODYzNy1iZmEwYjA1NTMyY2IiLCJjb21tdW5pdHlfc3VwcG9ydGVkIjpmYWxzZSwiZXhwIjoxNjg0NTU0ODgzLCJnZW5kZXIiOiIiLCJoaXRzX3Blcl9kYXkiOjEwMDAwMDAwMCwiaGl0c19wZXJfbW9udGgiOjEwMDAwMDAwMCwiaWQiOiIyNDIwNjQxIiwiaXNfbW9kIjpmYWxzZSwiaXNfc3lzdGVtX2tleSI6ZmFsc2UsImlzX3RydXN0ZWQiOmZhbHNlLCJwaW4iOm51bGwsInJvbGVzIjpbXSwidGVuYW50IjoidHZkYiIsInV1aWQiOiIifQ.S13Ig6uFvtuwBwWrjWQjqCCVuXsNDZDzrDyLGQTMcYnnznUp0NQZmI8vJrcHuELUFdW-i2lSPWEQ3vt9aGIx7w2O5ElHGr0LubSSynr-F0JkCIYt9rB2_RX_L_YDEn4sIpZUJXdALp-HzhCAnfzrFSPPPOHurJYXRHlNa5mxz6u-Qi0DI5iEzcmrHIIA7Q3N30Q4KqILi1k9l_rEQYWdLfzfr6iv0AJzhgQLPHbAK1GYrEJUZkYRaJE0xrt8ub32fx-JQVOkPZQbkxo0y_EOcrglBsXgke1rv_WyTggBuppgCS1TyshaUPVFkV_2PniABF4MyjAXdnPgj3RHZKA0SxhaxyhRTsUE3TpV7nSMkYoEu7T5-AcgM7259d4bjpGqEIA6xYyEuyhBSjATknFdDhBz5ar_3txVbEMOJJ1MyFZ-GYz-YOeWc5QOv9G8uOjJfdVnk-VGTqIJKYIbAdg8oz6SiMqQVky-4LaDAv4it8KdgzOOZ397ABmWEZjm-2rTkUwBAD24yf7zThnK7y35YxxlFRc9Yules59LnBxMyJKeZN1nuV-xPy5VukzA95cJ7Q3eOeQeSorNtjJFCRlBVHD0h9hHpu5uCQHPverKqKSkE97UXcIA9wVI29JJtjuiJ0-IbBUXnMWHPEG8_SQLqW1vZXSbJcsk72aXZaWydeY";

    /** TESTING RETRIEVING ARTWORK **/
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
}
