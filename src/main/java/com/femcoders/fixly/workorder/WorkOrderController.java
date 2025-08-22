package com.femcoders.fixly.workorder;

import com.femcoders.fixly.workorder.dtos.CreateWorkOrderRequest;
import com.femcoders.fixly.workorder.dtos.CreateWorkOrderResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Workorder", description = "Workorder operations")
@RequestMapping("/api/workorders")
@RequiredArgsConstructor
public class WorkOrderController {
    private final WorkOrderService workOrderService;

    @PreAuthorize("hasAnyRole('CLIENT', 'ADMIN')")
    @PostMapping("")
    public ResponseEntity<CreateWorkOrderResponse> createWorkorder (@Valid @RequestBody CreateWorkOrderRequest request){
        CreateWorkOrderResponse workOrderResponse = workOrderService.createWorkOrder(request);
        return new ResponseEntity<>(workOrderResponse, HttpStatus.CREATED);
    }

}
