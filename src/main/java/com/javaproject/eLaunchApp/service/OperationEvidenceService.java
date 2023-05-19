package com.javaproject.eLaunchApp.service;

import com.javaproject.eLaunchApp.models.OperationEvidence;
import com.javaproject.eLaunchApp.models.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OperationEvidenceService {
	List<OperationEvidence> getAll();
	void add(OperationEvidence operationEvidence);
	void delete(OperationEvidence operationEvidence);
	BigDecimal getUserAccountBalance(User user);
	BigDecimal getAccountBalanceAfterOperation(OperationEvidence operationEvidence);
}
