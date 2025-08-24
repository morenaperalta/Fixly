package com.femcoders.fixly.user.dtos;

import com.femcoders.fixly.user.User;

public final class UserMapper {
    private UserMapper() {
    }

    public static User userUpdateToEntity(UserUpdateRequest request){
        return User.builder().username(request.username().trim()).email(request.email().trim()).firstName(request.firstName().trim()).lastName(request.lastName().trim()).company(request.company().trim()).build();
    }

    public static User adminUpdateToEntity(UserUpdateRequestForAdmin request){
        return User.builder().username(request.username().trim()).email(request.email().trim()).firstName(request.firstName().trim()).lastName(request.lastName().trim()).company(request.company().trim()).role(request.role()).build();
    }

    public static UserResponse userResponseToDto(User user){
        return new UserResponse(user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getCompany());
    }

    public static UserResponseForAdmin adminResponseToDto(User user){
        return new UserResponseForAdmin(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName(), user.getCompany(), user.getRole(), user.getCreatedAt(), user.getUpdatedAt());
    }

    public static UserSummaryResponse userSummaryResponseToDto(User user) {
        return new UserSummaryResponse(user.getId(), user.getUsername(), user.getFirstName(), user.getLastName());
    }
}