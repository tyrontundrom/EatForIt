package com.tyrontundrom.eatforit.listeners;

import com.tyrontundrom.eatforit.dto.UserDto;
import com.tyrontundrom.eatforit.events.OperationEvidenceCreator;
import com.tyrontundrom.eatforit.model.OperationEvidence;
import com.tyrontundrom.eatforit.model.User;
import com.tyrontundrom.eatforit.repo.UserJpaRepository;
import com.tyrontundrom.eatforit.service.OperationEvidenceService;
import com.tyrontundrom.eatforit.utils.ConverterUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@AllArgsConstructor
public class OperationEvidenceListener {

    private final OperationEvidenceService operationEvidenceService;
    private final UserJpaRepository userJpaRepository;

    @EventListener
    public void onAddOperation(OperationEvidenceCreator operationEvidenceCreator) {
        UserDto userDto = operationEvidenceCreator.getUserDto();
        OperationEvidence operationEvidence = ConverterUtils.convert(userDto.getOperationEvidenceDtos().stream()
                .findFirst().orElseThrow());
        User user = userJpaRepository.findByUuid(userDto.getUuid()).orElseThrow();
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
