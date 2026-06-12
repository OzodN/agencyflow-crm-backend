package com.agencyflow.crm.customer.controller;

import com.agencyflow.crm.common.exception.ErrorResponse;
import com.agencyflow.crm.customer.dto.CustomerResponse;
import com.agencyflow.crm.customer.dto.UpdateCustomerRequest;
import com.agencyflow.crm.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customers", description = "Authenticated endpoints for reading, updating, and soft-deleting customers created from converted leads.")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
            summary = "List customers",
            description = "Returns all non-deleted customers. Customers are created only by converting qualified leads; there is no direct customer creation endpoint in the current API. The current implementation does not paginate, sort, or filter this list.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Active customers were returned successfully.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CustomerResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Authentication is required, the bearer token is missing, or the bearer token is invalid.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error while listing customers.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<CustomerResponse> getAll() {
        return customerService.getAll();
    }

    @Operation(
            summary = "Get customer by ID",
            description = "Returns one non-deleted customer by its server-generated identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer was found and returned.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Customer identifier path variable is not a valid number.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required, the bearer token is missing, or the bearer token is invalid.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No active customer exists for the supplied identifier.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error while retrieving the customer.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public CustomerResponse getById(
            @Parameter(description = "Server-generated identifier of the active customer to retrieve.", required = true, schema = @Schema(type = "integer", format = "int64", example = "501"))
            @PathVariable Long id
    ) {
        return customerService.getById(id);
    }

    @Operation(
            summary = "Update customer contact details",
            description = "Updates editable contact fields on an active customer. Null fields are ignored by the current implementation, so consumers may send only the fields they want to change.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Customer contact fields to update; omitted or null fields are left unchanged.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateCustomerRequest.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer contact details were updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request body is malformed or the customer identifier path variable is not a valid number.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required, the bearer token is missing, or the bearer token is invalid.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No active customer exists for the supplied identifier.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error while updating the customer.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public CustomerResponse update(
            @Parameter(description = "Server-generated identifier of the active customer to update.", required = true, schema = @Schema(type = "integer", format = "int64", example = "501"))
            @PathVariable Long id,
            @RequestBody @Valid UpdateCustomerRequest request
    ) {
        return customerService.update(id, request);
    }

    @Operation(
            summary = "Delete customer",
            description = "Soft-deletes an active customer. Deleted customers are excluded from customer read operations.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer was soft-deleted. The response body is empty."),
            @ApiResponse(responseCode = "400", description = "Customer identifier path variable is not a valid number.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required, the bearer token is missing, or the bearer token is invalid.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No active customer exists for the supplied identifier.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error while deleting the customer.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "Server-generated identifier of the active customer to soft-delete.", required = true, schema = @Schema(type = "integer", format = "int64", example = "501"))
            @PathVariable Long id
    ) {
        customerService.delete(id);
    }
}
