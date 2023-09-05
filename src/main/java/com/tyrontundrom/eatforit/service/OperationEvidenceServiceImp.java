package com.tyrontundrom.eatforit.service;

import com.tyrontundrom.eatforit.model.OperationEvidence;
import com.tyrontundrom.eatforit.model.User;
import com.tyrontundrom.eatforit.repo.DelivererJpaRepository;
import com.tyrontundrom.eatforit.repo.OperationEvidenceJpaRepository;
import com.tyrontundrom.eatforit.repo.OrderJpaRepository;
import com.tyrontundrom.eatforit.utils.ConverterUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OperationEvidenceServiceImp implements OperationEvidenceService {

    private final OperationEvidenceJpaRepository operationEvidenceJpaRepository;

    @Override
    public List<OperationEvidence> getAll() {
        return operationEvidenceJpaRepository.findAll();
    }

    @Override
    public void add(OperationEvidence operationEvidence) {
        operationEvidenceJpaRepository.save(operationEvidence);
    }

    @Override
    public void delete(OperationEvidence operationEvidence) {
        operationEvidenceJpaRepository.delete(operationEvidence);
    }

    @Override
    public BigDecimal getUserAccountBalance(User user) {
        return operationEvidenceJpaRepository.getUserAccountBalance(user);
    }

    @Override
    public BigDecimal getAccountBalanceAfterOperation(OperationEvidence operationEvidence) {
        BigDecimal balanceBefore = getUserAccountBalance(operationEvidence.getUser());
        BigDecimal balanceAfter;

        switch (operationEvidence.getEvidenceType()) {
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
