package fr.jasonbailleul.services;

import fr.jasonbailleul.dto.ApiKeyDto;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;


@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ClientServiceTest {

    private static int id;
    private ApiKeyDto apiKeyDto;


    @Test
    @Order(1)
    void insert() {
        apiKeyDto = new ApiKeyDto();
        apiKeyDto.setNom("test");
        apiKeyDto.setMail("test@example.com");
        apiKeyDto.setQuota(100);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(apiKeyDto)
                .when()
                .post("/client/")
                .then()
                .statusCode(200)
                .extract().response();
        id = Integer.parseInt(response.asString());
        System.out.println(id);
    }

    @Test
    @Order(2)
    void getAll() {
        given()
                .when()
                .get("/client/")
                .then()
                .statusCode(200);
    }

    @Test
    @Order(3)
    void delete() {
        System.out.println();
        given()
                .pathParam("id", id)
                .when()
                .delete("client/{id}")
                .then()
                .statusCode(200);
    }




}