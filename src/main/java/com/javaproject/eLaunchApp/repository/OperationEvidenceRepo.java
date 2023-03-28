package com.javaproject.eLaunchApp.repository;

import com.javaproject.eLaunchApp.models.Deliverer;
import com.javaproject.eLaunchApp.models.DeliveryAddress;
import com.javaproject.eLaunchApp.models.OperationEvidence;
import com.javaproject.eLaunchApp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperationEvidenceRepo extends JpaRepository<OperationEvidence, Long> {

    @Query("SELECT COALESCE(SUM(" +
            "CASE " +
            "WHEN e.type = com.javaproject.eLaunchApp.models.enums.EvidenceType.DEPOSIT THEN e.amount " +
            "WHEN e.type = com.javaproject.eLaunchApp.models.enums.EvidenceType.WITHDRAW " +
            "or e.type = com.javaproject.eLaunchApp.models.enums.EvidenceType.PAYMENT THEN -e.amount " +
            "ELSE 0 " +
            "END" +
            "), 0 ) from OperationEvidence e WHERE e.user = :user")
    BigDecimal getUserAccountBalance(@Param("user") User user);
}
