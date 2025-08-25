package com.femcoders.fixly.workorder;

import com.femcoders.fixly.workorder.services.WorkOrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for WorkOrderService")
public class WorkOrderServiceTest {

    @Mock
    private WorkOrderRepository workOrderRepository;

    @InjectMocks
    private WorkOrderService workOrderService;

    private WorkOrder workorder1;
    private WorkOrder workOrder2;


}
