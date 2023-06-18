package com.tyrontundrom.eatforit.dto;

import com.tyrontundrom.eatforit.model.enums.Archive;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
}
