package clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;

import static io.restassured.RestAssured.given;

public class ApiClient {

    private final UserApiClient users;

    @Setter
    @Getter
    private String token;

    public ApiClient() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
        users = new UserApiClient(this);
    }

    public UserApiClient users() {
        return users;
    }

    public Response get(String path) {
        return given()
                .header("Authorization", token)
                .get(path);
    }

    public Response post(String path, Object body) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .body(body)
                .post(path);
    }
}
