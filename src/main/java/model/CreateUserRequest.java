package model;

import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String email;
    private String password;
    private String name;

    public CreateUserRequest withEmail(String email) {
        this.email = email;
        return this;
    }

    public CreateUserRequest withPassword(String password) {
        this.password = password;
        return this;
    }

    public CreateUserRequest withName(String name) {
        this.name = name;
        return this;
    }
}
