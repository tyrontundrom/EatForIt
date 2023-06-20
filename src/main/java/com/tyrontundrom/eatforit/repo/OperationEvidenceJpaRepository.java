package com.tyrontundrom.eatforit.repo;

import com.tyrontundrom.eatforit.model.OperationEvidence;
import com.tyrontundrom.eatforit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperationEvidenceJpaRepository extends JpaRepository<OperationEvidence, Long> {

    @Query("SELECT COALESCE(SUM" +
            "(CASE " +
            "WHEN e.evidenceType = com.tyrontundrom.eatforit.model.enums.EvidenceType.DEPOSIT THEN e.amount " +
            "WHEN e.evidenceType = com.tyrontundrom.eatforit.model.enums.EvidenceType.WITHDRAW " +
            "or e.evidenceType = com.tyrontundrom.eatforit.model.enums.EvidenceType.PAYMENT THEN -e.amount " +
            "ELSE 0 END ), 0) from OperationEvidence e where e.user = :user")
    BigDecimal getUserAccountBalance(@Param("user")User user);
}
