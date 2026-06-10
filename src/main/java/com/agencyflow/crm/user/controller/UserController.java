package com.agencyflow.crm.user.controller;

import com.agencyflow.crm.common.exception.ErrorResponse;
import com.agencyflow.crm.user.dto.UserResponse;
import com.agencyflow.crm.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Endpoints for reading and managing users")
@SecurityRequirement(name = "BearerAuth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Get user details",
            description = "Returns a single user by ID. Requires authenticated access via JWT bearer token."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User found and returned",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required or invalid JWT token",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "User not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    public UserResponse getById(
            @Parameter(description = "Unique identifier of the user", required = true, schema = @Schema(type = "integer", format = "int64", example = "1"))
            @PathVariable Long id
    ) {
        return userService.getById(id);
    }

    @Operation(
            summary = "List users",
            description = "Returns a list of all users. Requires authenticated access via JWT bearer token."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "List of users returned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Authentication required or invalid JWT token",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping
    public List<UserResponse> getAll() {
        return userService.getAll();
    }
}
