package com.femcoders.fixly.workorder;

import com.femcoders.fixly.workorder.dtos.CreateWorkOrderRequest;
import com.femcoders.fixly.workorder.dtos.WorkOrderResponse;
import com.femcoders.fixly.workorder.dtos.WorkOrderResponseForAdminAndSupervisor;
import com.femcoders.fixly.workorder.dtos.WorkOrderResponseForTechnician;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Work Order", description = "Work order operations")
@RequestMapping("/api/workorders")
@RequiredArgsConstructor
public class WorkOrderController {
    private final WorkOrderService workOrderService;


    @Operation(summary = "Create work order", description = "Create a work order. Available to CLIENT and ADMIN roles."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Work order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PostMapping("")
    public ResponseEntity<WorkOrderResponse> createWorkOrder(@Valid @RequestBody CreateWorkOrderRequest request){
        WorkOrderResponse workOrderResponse = workOrderService.createWorkOrder(request);
        return new ResponseEntity<>(workOrderResponse, HttpStatus.CREATED);
    }

    @Operation(summary = "Get work orders", description = "Retrieve a list of all work orders. Available to ADMIN roles."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of work orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<WorkOrderResponseForAdminAndSupervisor>> getAllWorkOrders(){
        List<WorkOrderResponseForAdminAndSupervisor> workOrderResponses = workOrderService.getAllWorkOrders();
        return new ResponseEntity<>(workOrderResponses, HttpStatus.OK);
    }

<<<<<<< Updated upstream
    @Operation(summary = "Get all work orders assigned", description = "Retrieve a list of all work orders assigned to authenticated user with role TECHNICIAN"
=======
    @Operation(summary = "Get work orders assigned", description = "Retrieve a list of all work orders assigned to authenticated user with role TECHNICIAN."
>>>>>>> Stashed changes
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of work orders retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('TECHNICIAN')")
    @GetMapping("/assigned")
<<<<<<< Updated upstream
    public ResponseEntity<List<WorkOrderResponseForTechnician>> getAllWorkOrdersAssigned(){
        List<WorkOrderResponseForTechnician> workOrderResponses = workOrderService.getWorkOrdersAssigned();
        return new ResponseEntity<>(workOrderResponses, HttpStatus.OK);
    }


=======
    public ResponseEntity<List<WorkOrderResponse>> getAllWorkOrdersAssigned(){
        List<WorkOrderResponse> workOrderResponses = workOrderService.getWorkOrdersAssigned();
        return new ResponseEntity<>(workOrderResponses, HttpStatus.OK);
    }
>>>>>>> Stashed changes
}
