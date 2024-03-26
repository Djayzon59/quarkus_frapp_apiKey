package fr.jasonbailleul.services;

import fr.jasonbailleul.dto.MailDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class ApiKeyServiceTest {


   @Test
    void updateQuota() {

        given()
                .contentType(ContentType.JSON)
                .pathParams("id", 3)
                .queryParam("quota", 600)
                .when()
                .put("/apiKey/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    void chekUsage() {
        given()
                .contentType(ContentType.JSON)
                .header("clef","t56J6FiHFI8+dA==")
                .when()
                .get("/apiKey/")
                .then()
                .statusCode(200);
    }
    @Test
    void chekUsageInvalidKey() {
        given()
                .contentType(ContentType.JSON)
                .header("clef","t56J6FFI8+dA==")
                .when()
                .get("/apiKey/")
                .then()
                .statusCode(250);
    }


    @Test
    void saveMail() {
        MailDto mailDto = new MailDto("test", "djadja59670@gmail.com","Hello World");

        given()
                .contentType(ContentType.JSON)
                .header("clef","t56J6FFI8+dA==")
                .body(mailDto)
                .when()
                .post("/apiKey/")
                .then()
                .statusCode(200);
    }


}