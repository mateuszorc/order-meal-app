package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.DTO.DelivererDTO;
import com.javaproject.eLaunchApp.models.Deliverer;
import com.javaproject.eLaunchApp.models.OperationEvidence;
import com.javaproject.eLaunchApp.models.User;
import com.javaproject.eLaunchApp.repository.DelivererRepo;
import com.javaproject.eLaunchApp.repository.OperationEvidenceRepo;
import com.javaproject.eLaunchApp.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OperationEvidenceServiceImpl implements OperationEvidenceService {

    private final OperationEvidenceRepo operationEvidenceRepo;

    @Autowired
    public OperationEvidenceServiceImpl(OperationEvidenceRepo operationEvidenceRepo) {
        this.operationEvidenceRepo = operationEvidenceRepo;
    }


    @Override
    public List<OperationEvidence> getAll() {
        return operationEvidenceRepo.findAll();
    }

    @Override
    public void add(OperationEvidence operationEvidence) {
        operationEvidenceRepo.save(operationEvidence);
    }

    @Override
    public void delete(OperationEvidence operationEvidence) {
        operationEvidenceRepo.delete(operationEvidence);
    }

    @Override
    public BigDecimal getUserAccountBalance(User user) {
        return operationEvidenceRepo.getUserAccountBalance(user);
    }

    @Override
    public BigDecimal getAccountBalanceAfterOperation(OperationEvidence operationEvidence) {
        BigDecimal balanceBefore = getUserAccountBalance(operationEvidence.getUser());
        BigDecimal balanceAfter;
        switch (operationEvidence.getType()) {
            case WITHDRAW:
            case PAYMENT:
                balanceAfter = balanceBefore.subtract(operationEvidence.getAmount());
                break;
            case DEPOSIT:
                balanceAfter = balanceBefore.add(operationEvidence.getAmount());
                break;
            default:
                throw new UnsupportedOperationException();
        }
        return balanceAfter;
    }
}
