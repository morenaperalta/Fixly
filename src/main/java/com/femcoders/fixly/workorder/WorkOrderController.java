package com.femcoders.fixly.workorder;

import com.femcoders.fixly.workorder.dtos.request.CreateWorkOrderRequest;
import com.femcoders.fixly.workorder.dtos.response.WorkOrderResponse;
import com.femcoders.fixly.workorder.dtos.response.WorkOrderSummaryResponse;
import com.femcoders.fixly.workorder.services.implementations.WorkOrderServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Work Order", description = "Work order operations")
@RequestMapping("/api/workorders")
@RequiredArgsConstructor
public class WorkOrderController {
    private final WorkOrderServiceImpl workOrderService;

    @Operation(summary = "Create work order", description = "Create a work order. Available to CLIENT role.")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Work order created successfully"), @ApiResponse(responseCode = "400", description = "Invalid request data"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden")})
    @PostMapping("")
    @PreAuthorize("hasAnyRole('CLIENT')")
    public ResponseEntity<WorkOrderSummaryResponse> createWorkOrder(@Valid @RequestBody CreateWorkOrderRequest request) {
        WorkOrderSummaryResponse workOrderResponse = workOrderService.createWorkOrder(request);
        return new ResponseEntity<>(workOrderResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all work orders", description = "Returns all work orders available to the authenticated user. The set of work orders is restricted based on the user’s role and associated permissions.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of work orders retrieved successfully"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden")})

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN', 'CLIENT', 'SUPERVISOR')")
    public ResponseEntity<List<WorkOrderResponse>> getAllWorkOrders(Authentication auth) {
        List<WorkOrderResponse> workOrderResponses = workOrderService.getAllWorkOrders(auth);
        return new ResponseEntity<>(workOrderResponses, HttpStatus.OK);
    }

    @Operation(summary = "Get work order by identifier,", description = "Returns the work order with the specified identifier if it is accessible to the authenticated user. Access is restricted based on the user’s role and associated permissions.")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of work orders retrieved successfully"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "403", description = "Forbidden"), @ApiResponse(responseCode = "404", description = "WorkOrder with id {id} not found")})
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN', 'CLIENT', 'SUPERVISOR')")
    @GetMapping("/{identifier}")
    public ResponseEntity<WorkOrderResponse> getWorkOrderByIdentifier(@PathVariable String identifier, Authentication auth) {
        WorkOrderResponse workOrderResponse = workOrderService.getWorkOrderByIdentifier(identifier, auth);
        return new ResponseEntity<>(workOrderResponse, HttpStatus.OK);
    }
}
