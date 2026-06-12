package com.agencyflow.crm.lead.controller;

import com.agencyflow.crm.common.exception.ErrorResponse;
import com.agencyflow.crm.lead.dto.CreateLeadRequest;
import com.agencyflow.crm.lead.dto.LeadResponse;
import com.agencyflow.crm.lead.dto.UpdateLeadRequest;
import com.agencyflow.crm.lead.dto.UpdateLeadStatusRequest;
import com.agencyflow.crm.lead.service.LeadService;
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
@RequestMapping("/api/v1/leads")
@Tag(name = "Leads", description = "Authenticated endpoints for managing sales leads, assignment, workflow status changes, and conversion to customers.")
@RequiredArgsConstructor
public class LeadController {

    private final LeadService leadService;

    @Operation(
            summary = "Create lead",
            description = "Creates a new sales lead in NEW status and assigns it to an existing SALES_MANAGER user. Customers cannot be created directly; a customer record is created later only through lead conversion.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lead details and required sales manager assignment.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateLeadRequest.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lead was created in NEW status.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LeadResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request validation failed, the assigned sales manager ID is missing, or the referenced user is not a SALES_MANAGER.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required, the bearer token is missing, or the bearer token is invalid.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Assigned sales manager user was not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error while creating the lead.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public LeadResponse create(@RequestBody @Valid CreateLeadRequest request) {
        return leadService.create(request);
    }

    @Operation(
            summary = "List leads",
            description = "Returns all non-deleted leads. The current implementation does not paginate, sort, or filter this list.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Active leads were returned successfully.", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = LeadResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Authentication is required, the bearer token is missing, or the bearer token is invalid.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error while listing leads.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    public List<LeadResponse> getAll() {
        return leadService.getAll();
    }

    @Operation(
            summary = "Get lead by ID",
            description = "Returns one non-deleted lead by its server-generated identifier.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lead was found and returned.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LeadResponse.class))),
            @ApiResponse(responseCode = "400", description = "Lead identifier path variable is not a valid number.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required, the bearer token is missing, or the bearer token is invalid.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No active lead exists for the supplied identifier.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error while retrieving the lead.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public LeadResponse getById(
            @Parameter(description = "Server-generated identifier of the active lead to retrieve.", required = true, schema = @Schema(type = "integer", format = "int64", example = "101"))
            @PathVariable Long id
    ) {
        return leadService.getById(id);
    }

    @Operation(
            summary = "Update lead contact details",
            description = "Updates editable contact fields on an active lead. Null fields are ignored by the current implementation, so consumers may send only the fields they want to change.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Lead contact fields to update; omitted or null fields are left unchanged.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateLeadRequest.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lead contact details were updated.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LeadResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request body is malformed or the lead identifier path variable is not a valid number.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required, the bearer token is missing, or the bearer token is invalid.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No active lead exists for the supplied identifier.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error while updating the lead.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public LeadResponse update(
            @Parameter(description = "Server-generated identifier of the active lead to update.", required = true, schema = @Schema(type = "integer", format = "int64", example = "101"))
            @PathVariable Long id,
            @RequestBody @Valid UpdateLeadRequest request
    ) {
        return leadService.update(id, request);
    }

    @Operation(
            summary = "Change lead status",
            description = "Moves an active lead through the implemented workflow. Valid transitions are NEW to CONTACTED or REJECTED, CONTACTED to QUALIFIED or REJECTED, and QUALIFIED to CONVERTED. CONVERTED and REJECTED leads cannot move to another status through this endpoint.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Target lead status to apply.",
                    required = true,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateLeadStatusRequest.class))
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lead status was changed.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LeadResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request validation failed, the status value is malformed, the lead identifier path variable is not a valid number, or the requested status transition is not allowed.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required, the bearer token is missing, or the bearer token is invalid.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No active lead exists for the supplied identifier.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error while changing lead status.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}/status")
    public LeadResponse changeStatus(
            @Parameter(description = "Server-generated identifier of the active lead whose status will change.", required = true, schema = @Schema(type = "integer", format = "int64", example = "101"))
            @PathVariable Long id,
            @RequestBody @Valid UpdateLeadStatusRequest request
    ) {
        return leadService.changeStatus(id, request);
    }

    @Operation(
            summary = "Assign sales manager",
            description = "Assigns an active lead to an existing user with the SALES_MANAGER role. The endpoint replaces any previous lead assignment.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lead was assigned to the sales manager.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LeadResponse.class))),
            @ApiResponse(responseCode = "400", description = "A path variable is not a valid number, or the referenced user exists but does not have the SALES_MANAGER role.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required, the bearer token is missing, or the bearer token is invalid.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Lead or sales manager user was not found.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error while assigning the lead.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}/assign/{salesManagerId}")
    public LeadResponse assignSalesManager(
            @Parameter(description = "Server-generated identifier of the active lead to assign.", required = true, schema = @Schema(type = "integer", format = "int64", example = "101"))
            @PathVariable Long id,
            @Parameter(description = "Server-generated identifier of the user who must have the SALES_MANAGER role.", required = true, schema = @Schema(type = "integer", format = "int64", example = "2"))
            @PathVariable Long salesManagerId
    ) {
        return leadService.assignSalesManager(id, salesManagerId);
    }

    @Operation(
            summary = "Delete lead",
            description = "Soft-deletes an active lead. Deleted leads are excluded from lead read operations.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lead was soft-deleted. The response body is empty."),
            @ApiResponse(responseCode = "400", description = "Lead identifier path variable is not a valid number.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required, the bearer token is missing, or the bearer token is invalid.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No active lead exists for the supplied identifier.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error while deleting the lead.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "Server-generated identifier of the active lead to soft-delete.", required = true, schema = @Schema(type = "integer", format = "int64", example = "101"))
            @PathVariable Long id
    ) {
        leadService.delete(id);
    }

    @Operation(
            summary = "Convert lead to customer",
            description = "Converts an active QUALIFIED lead into a customer. The service creates the customer from the lead's company and contact details, marks the lead CONVERTED, and stores the generated customer identifier and conversion timestamp. Customers cannot be created directly by a customer endpoint.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lead was converted and the updated lead includes conversion details.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LeadResponse.class))),
            @ApiResponse(responseCode = "400", description = "Lead identifier path variable is not a valid number, or the lead is not currently QUALIFIED and cannot be converted.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication is required, the bearer token is missing, or the bearer token is invalid.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "No active lead exists for the supplied identifier.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected server error while converting the lead.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/{id}/convert")
    public LeadResponse convert(
            @Parameter(description = "Server-generated identifier of the active QUALIFIED lead to convert.", required = true, schema = @Schema(type = "integer", format = "int64", example = "101"))
            @PathVariable Long id
    ) {
        return leadService.convert(id);
    }
}
