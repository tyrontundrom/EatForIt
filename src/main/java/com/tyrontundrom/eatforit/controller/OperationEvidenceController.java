package com.tyrontundrom.eatforit.controller;

import com.tyrontundrom.eatforit.dto.DelivererDto;
import com.tyrontundrom.eatforit.dto.OperationEvidenceDto;
import com.tyrontundrom.eatforit.model.OperationEvidence;
import com.tyrontundrom.eatforit.service.DelivererService;
import com.tyrontundrom.eatforit.service.OperationEvidenceService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(path = "api/operation-evidences", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
class OperationEvidenceController {

    private final OperationEvidenceService operationEvidenceService;

    @GetMapping
    public List<OperationEvidenceDto> getAll() {
        return null;
    }

    @GetMapping("{uuid}")
    public OperationEvidenceDto get(@PathVariable UUID uuid) {
        return null;
    }

    @Transactional
    @PutMapping("{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody OperationEvidenceDto operationEvidenceDto) {

    }

    @Transactional
    @DeleteMapping("{uuid}")
    public void delete(@PathVariable UUID uuid) {

    }
}
