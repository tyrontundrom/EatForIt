package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.model.*;
import com.tyrontundrom.eatforit.model.enums.Archive;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

class UserDto {
    @NotNull
    private UUID uuid;

    @NotNull
    @Embedded
    private PersonalDataDto personalDatadto;

    @Nullable
    private List<DeliveryAddressDto> deliveryAddressDtos;

    @NotNull
    @Embedded
    private LogginDataDto logginDataDto;

    @Nullable
    private List<OrderDto> orderDtos;

    @NotNull
    private List<OperationEvidenceDto> operationEvidenceDtos;

    @Nullable
    private List<DiscountCodeDto> discountCodeDtos;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Archive archive;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public PersonalDataDto getPersonalDatadto() {
        return personalDatadto;
    }

    public void setPersonalDatadto(PersonalDataDto personalDatadto) {
        this.personalDatadto = personalDatadto;
    }

    @Nullable
    public List<DeliveryAddressDto> getDeliveryAddressDtos() {
        return deliveryAddressDtos;
    }

    public void setDeliveryAddressDtos(@Nullable List<DeliveryAddressDto> deliveryAddressDtos) {
        this.deliveryAddressDtos = deliveryAddressDtos;
    }

    public LogginDataDto getLogginDataDto() {
        return logginDataDto;
    }

    public void setLogginDataDto(LogginDataDto logginDataDto) {
        this.logginDataDto = logginDataDto;
    }

    @Nullable
    public List<OrderDto> getOrderDtos() {
        return orderDtos;
    }

    public void setOrderDtos(@Nullable List<OrderDto> orderDtos) {
        this.orderDtos = orderDtos;
    }

    public List<OperationEvidenceDto> getOperationEvidenceDtos() {
        return operationEvidenceDtos;
    }

    public void setOperationEvidenceDtos(List<OperationEvidenceDto> operationEvidenceDtos) {
        this.operationEvidenceDtos = operationEvidenceDtos;
    }

    @Nullable
    public List<DiscountCodeDto> getDiscountCodeDtos() {
        return discountCodeDtos;
    }

    public void setDiscountCodeDtos(@Nullable List<DiscountCodeDto> discountCodeDtos) {
        this.discountCodeDtos = discountCodeDtos;
    }

    public Archive getArchive() {
        return archive;
    }

    public void setArchive(Archive archive) {
        this.archive = archive;
    }
}
