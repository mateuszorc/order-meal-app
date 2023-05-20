package com.javaproject.eLaunchApp.listeners;

import com.javaproject.eLaunchApp.DTO.UserDTO;
import com.javaproject.eLaunchApp.events.OperationEvidenceCreator;
import com.javaproject.eLaunchApp.models.OperationEvidence;
import com.javaproject.eLaunchApp.models.User;
import com.javaproject.eLaunchApp.repository.UserRepo;
import com.javaproject.eLaunchApp.service.OperationEvidenceService;
import com.javaproject.eLaunchApp.utils.ConvertUtils;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Component
public class OperationEvidenceListener {

    private final OperationEvidenceService operationEvidenceService;
    private final UserRepo userRepo;

    public OperationEvidenceListener(OperationEvidenceService operationEvidenceService, UserRepo userRepo) {
        this.operationEvidenceService = operationEvidenceService;
        this.userRepo = userRepo;
    }

    @EventListener
    public void onAddOperation(OperationEvidenceCreator operationEvidenceCreator) {
        UserDTO userDTO = operationEvidenceCreator.getUserDTO();
        OperationEvidence operationEvidence = ConvertUtils.convert(userDTO.getOperationEvidences().stream()
                .findFirst()
                .orElseThrow());
        User user = userRepo.findByUuid(userDTO.getUuid()).orElseThrow();
        operationEvidence.setUser(user);

        validateAccountBalanceAfterOperation(operationEvidence);
        operationEvidenceService.add(operationEvidence);
    }

    private void validateAccountBalanceAfterOperation(OperationEvidence operationEvidence) {
        BigDecimal accountBalanceAfterOperation = operationEvidenceService.getAccountBalanceAfterOperation(operationEvidence);
        if (accountBalanceAfterOperation.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
