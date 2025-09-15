package clients;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CreateUserRequest;

import static io.restassured.RestAssured.given;

public record UserApiClient(ApiClient apiClient) {

    @Step("Создание пользователя: {0}")
    public Response create(CreateUserRequest user) {
        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .post("/api/auth/register");

        if (response.statusCode() == 200) {
            String token = response.path("accessToken");
            apiClient.setToken(token);
        }
        return response;
    }

    @Step("Удаление пользователя")
    public Response delete() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", apiClient.getToken())
                .delete("/api/auth/user");
    }
}
