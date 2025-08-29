package com.femcoders.fixly.user;

import com.femcoders.fixly.shared.exception.ErrorResponse;
import com.femcoders.fixly.user.dtos.UserResponse;
import com.femcoders.fixly.user.dtos.UserResponseForAdmin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "User", description = "User operations")
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get users", description = "Retrieve a list of all users. Available to SUPERVISOR and ADMIN roles.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of users retrieved successfully", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponseForAdmin.class)))), @ApiResponse(responseCode = "401", description = "Authentication required - JWT token missing or invalid", content = @Content(schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "403", description = "Access denied - Insufficient permissions (requires ADMIN or SUPERVISOR role)", content = @Content(schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("")
    @PreAuthorize("hasAnyRole('SUPERVISOR', 'ADMIN')")
    public ResponseEntity<List<UserResponse>> getAllUsers(Authentication auth) {
        List<UserResponse> userResponses = userService.getAllUsers(auth);
        return new ResponseEntity<>(userResponses, HttpStatus.OK);
    }

    @Operation(summary = "Get own profile", description = "Retrieve profile of the authenticated user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User profile retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))), @ApiResponse(responseCode = "401", description = "Authentication required - JWT token missing or invalid", content = @Content(schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "403", description = "Access denied - Insufficient permissions", content = @Content(schema = @Schema(implementation = ErrorResponse.class))), @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> getOwnProfile(Authentication auth) {
        UserResponse userResponse = userService.getOwnProfile(auth);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
