package com.femcoders.fixly.user.dtos;

import com.femcoders.fixly.user.User;

public class UserMapper {
    public static User userUpdateToEntity(UserUpdateRequest request){
        return User.builder().username(request.username().trim()).email(request.email().trim()).firstName(request.firstName().trim()).lastName(request.lastName().trim()).company(request.company().trim()).build();
    }

    public static User adminUpdateToEntity(AdminUpdateRequest request){
        return User.builder().username(request.username().trim()).email(request.email().trim()).firstName(request.firstName().trim()).lastName(request.lastName().trim()).company(request.company().trim()).role(request.role()).build();
    }

    public static UserResponse userResponseToDto(User user){
        return new UserResponse(user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getCompany());
    }

    public static AdminResponse adminResponseToDto(User user){
        return new AdminResponse(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getCompany(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt());
    }
}