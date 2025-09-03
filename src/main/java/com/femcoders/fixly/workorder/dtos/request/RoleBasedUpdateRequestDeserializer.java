package com.femcoders.fixly.workorder.dtos.request;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.femcoders.fixly.workorder.entities.Priority;
import com.femcoders.fixly.workorder.entities.Status;
import com.femcoders.fixly.workorder.entities.SupervisionStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoleBasedUpdateRequestDeserializer extends JsonDeserializer<UpdateWorkOrderRequest> {

    @Override
    public UpdateWorkOrderRequest deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new IllegalStateException("No authentication available during deserialization");
        }

        String role = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("NO_ROLE");

        return switch (role) {
            case "ROLE_ADMIN" -> createAdminRequest(node);
            case "ROLE_SUPERVISOR" -> createSupervisorRequest(node);
            case "ROLE_TECHNICIAN" -> createTechnicianRequest(node);
            default -> throw new IllegalArgumentException("Role cannot update work orders: " + role);
        };
    }

    private UpdateWorkOrderRequestForAdmin createAdminRequest(JsonNode node) {
        Priority priority = extractPriority(node);
        Status status = extractStatus(node);
        SupervisionStatus supervisionStatus = extractSupervisionStatus(node);
        List<Long> technicianIds = extractTechnicianIds(node);
        Long supervisorId = extractSupervisorId(node);

        return new UpdateWorkOrderRequestForAdmin(priority, status, supervisionStatus, technicianIds, supervisorId);
    }

    private UpdateWorkOrderRequestForSupervisor createSupervisorRequest(JsonNode node) {
        Priority priority = extractPriority(node);
        Status status = extractStatus(node);
        SupervisionStatus supervisionStatus = extractSupervisionStatus(node);
        List<Long> technicianIds = extractTechnicianIds(node);

        return new UpdateWorkOrderRequestForSupervisor(priority, status, supervisionStatus, technicianIds);
    }

    private UpdateWorkOrderRequestForTechnician createTechnicianRequest(JsonNode node) {
        Status status = extractStatus(node);
        return new UpdateWorkOrderRequestForTechnician(status);
    }

    private Priority extractPriority(JsonNode node) {
        JsonNode priorityNode = node.get("priority");
        if (priorityNode == null || priorityNode.isNull()) {
            return null;
        }
        return Priority.valueOf(priorityNode.asText());
    }

    private Status extractStatus(JsonNode node) {
        JsonNode statusNode = node.get("status");
        if (statusNode == null || statusNode.isNull()) {
            return null;
        }
        return Status.valueOf(statusNode.asText());
    }

    private SupervisionStatus extractSupervisionStatus(JsonNode node) {
        JsonNode supervisionStatusNode = node.get("supervisionStatus");
        if (supervisionStatusNode == null || supervisionStatusNode.isNull()) {
            return null;
        }
        return SupervisionStatus.valueOf(supervisionStatusNode.asText());
    }

    private List<Long> extractTechnicianIds(JsonNode node) {
        JsonNode technicianIdsNode = node.get("technicianIds");
        if (technicianIdsNode == null || technicianIdsNode.isNull() || !technicianIdsNode.isArray()) {
            return null;
        }

        List<Long> technicianIds = new ArrayList<>();
        for (JsonNode idNode : technicianIdsNode) {
            if (!idNode.isNull()) {
                technicianIds.add(idNode.asLong());
            }
        }
        return technicianIds.isEmpty() ? null : technicianIds;
    }

    private Long extractSupervisorId(JsonNode node) {
        JsonNode supervisorIdNode = node.get("supervisorId");
        if (supervisorIdNode == null || supervisorIdNode.isNull()) {
            return null;
        }
        return supervisorIdNode.asLong();
    }
}
