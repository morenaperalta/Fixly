package com.femcoders.fixly.auth.dtos;

import com.femcoders.fixly.user.entities.User;

public class AuthMapper {
    public static User registrationToEntity(RegistrationRequest request) {
        return User.builder().username(request.username().trim()).email(request.email().trim()).password(request.password()).firstName(request.firstName().trim()).lastName(request.lastName().trim()).company(request.company().trim()).build();
    }
}
