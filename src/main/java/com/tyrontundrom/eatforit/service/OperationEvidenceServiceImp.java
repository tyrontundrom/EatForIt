package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.model.OperationEvidence;
import com.tyrontundrom.eatforit.model.User;
import com.tyrontundrom.eatforit.repo.DelivererJpaRepository;
import com.tyrontundrom.eatforit.repo.OperationEvidenceJpaRepository;
import com.tyrontundrom.eatforit.repo.OrderJpaRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
class OperationEvidenceServiceImp implements OperationEvidenceService {

    private final OperationEvidenceJpaRepository operationEvidenceJpaRepository;

    @Override
    public List<OperationEvidence> getAll() {
        return null;
    }

    @Override
    public void put(UUID uuid, OperationEvidence operationEvidence) {

    }

    @Override
    public void delete(UUID uuid) {

    }

    @Override
    public Optional<OperationEvidence> getByUuid(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public BigDecimal getUserAccountBalance(User user) {
        return null;
    }

    @Override
    public BigDecimal getAccountBalanceAfterOperation(OperationEvidence operationEvidence) {
        return null;
    }
}
