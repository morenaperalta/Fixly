package com.femcoders.fixly.workorder;

import com.femcoders.fixly.workorder.dtos.request.CreateWorkOrderRequest;
import com.femcoders.fixly.workorder.dtos.response.*;
import com.femcoders.fixly.workorder.services.WorkOrderServiceImpl;
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

    @Operation(summary = "Create work order", description = "Create a work order. Available to CLIENT and ADMIN roles."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Work order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping("")
    public ResponseEntity<WorkOrderSummaryResponse> createWorkOrder(@Valid @RequestBody CreateWorkOrderRequest request){
        WorkOrderSummaryResponse workOrderResponse = workOrderService.createWorkOrder(request);
        return new ResponseEntity<>(workOrderResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Get all work orders", description = "Retrieve a list of all work orders. Available to ADMIN roles."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of work orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })

    @GetMapping("")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN', 'CLIENT', 'SUPERVISOR')")
    public ResponseEntity<List<WorkOrderResponse>> getAllWorkOrders(Authentication auth){
        List<WorkOrderResponse> workOrderResponses = workOrderService.getAllWorkOrders(auth);
        return new ResponseEntity<>(workOrderResponses, HttpStatus.OK);
    }

//    @Operation(summary = "Get work orders assigned", description = "Retrieve a list of all work orders assigned to authenticated user with role TECHNICIAN."
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "List of work orders retrieved successfully"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized"),
//            @ApiResponse(responseCode = "403", description = "Forbidden")
//    })
//    @PreAuthorize("hasRole('TECHNICIAN')")
//    @GetMapping("/assigned")
//    public ResponseEntity<List<WorkOrderResponseForTechnician>> getAllWorkOrdersAssigned(){
//        List<WorkOrderResponseForTechnician> workOrderResponses = workOrderService.getWorkOrdersAssigned();
//        return new ResponseEntity<>(workOrderResponses, HttpStatus.OK);
//    }
//
//    @Operation(summary = "Get work orders supervised", description = "Retrieve a list of all work orders supervised by authenticated user with role SUPERVISOR."
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "List of work orders retrieved successfully"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized"),
//            @ApiResponse(responseCode = "403", description = "Forbidden")
//    })
//    @PreAuthorize("hasRole('SUPERVISOR')")
//    @GetMapping("/supervised")
//    public ResponseEntity<List<WorkOrderResponseForAdmin>> getAllWorkOrdersSupervised(){
//        List<WorkOrderResponseForAdmin> workOrderResponses = workOrderService.getWorkOrdersSupervised();
//        return new ResponseEntity<>(workOrderResponses, HttpStatus.OK);
//    }
//
//    @Operation(summary = "Get work orders created", description = "Retrieve a list of all work orders created by authenticated user with role CLIENT."
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "List of work orders retrieved successfully"),
//            @ApiResponse(responseCode = "401", description = "Unauthorized"),
//            @ApiResponse(responseCode = "403", description = "Forbidden")
//    })
//    @PreAuthorize("hasRole('CLIENT')")
//    @GetMapping("/created")
//    public ResponseEntity<List<WorkOrderResponseForClient>> getAllWorkOrdersCreatedByClient(){
//        List<WorkOrderResponseForClient> workOrderResponses = workOrderService.getWorkOrdersCreatedByClient();
//        return new ResponseEntity<>(workOrderResponses, HttpStatus.OK);
//    }

    @Operation(summary = "Get work order by id", description = "Retrieve work order by id. Available for authenticated user with role ADMIN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of work orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "WorkOrder with id {id} not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<WorkOrderResponseForAdmin> getWorkOrderByIdByAdmin(@PathVariable Long id) {
        WorkOrderResponseForAdmin workOrderResponse = workOrderService.getWorkOrderByIdForAdmin(id);
        return new ResponseEntity<>(workOrderResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get work order supervised by id", description = "Retrieve work order supervised by id. Available for authenticated user with role SUPERVISOR."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of work orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "WorkOrder with id {id} not found")
    })
    @PreAuthorize("hasRole('SUPERVISOR')")
    @GetMapping("/supervised/{id}")
    public ResponseEntity<WorkOrderResponseForAdmin> getWorkOrderByIdBySupervisor(@PathVariable Long id) {
        WorkOrderResponseForAdmin workOrderResponse = workOrderService.getWorkOrderByIdForSupervisor(id);
        return new ResponseEntity<>(workOrderResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get work order assigned by identifier", description = "Retrieve work order assigned by identifier. Available for authenticated user with role TECHNICIAN."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of work orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "WorkOrder with id {id} not found")
    })
    @PreAuthorize("hasRole('TECHNICIAN')")
    @GetMapping("/assigned/{identifier}")
    public ResponseEntity<WorkOrderResponseForTechnician> getWorkOrderByIdentifierByTechnician(@PathVariable String identifier) {
        WorkOrderResponseForTechnician workOrderResponse = workOrderService.getWorkOrderByIdentifierForTechnician(identifier);
        return new ResponseEntity<>(workOrderResponse, HttpStatus.OK);
    }

    @Operation(summary = "Get work order created by identifier", description = "Retrieve work order created by identifier. Available for authenticated user with role CLIENT."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of work orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden"),
            @ApiResponse(responseCode = "404", description = "WorkOrder with id {id} not found")
    })
    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping("/created/{identifier}")
    public ResponseEntity<WorkOrderResponseForClient> getWorkOrderByIdentifierByClient(@PathVariable String identifier) {
        WorkOrderResponseForClient workOrderResponse = workOrderService.getWorkOrderByIdentifierForClient(identifier);
        return new ResponseEntity<>(workOrderResponse, HttpStatus.OK);
    }
}
