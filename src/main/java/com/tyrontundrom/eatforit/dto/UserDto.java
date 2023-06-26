package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
public class UserDto {

    public static class View {
        public interface Id {}
        public interface Basic extends Id {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Id.class)
    @NotNull
    private UUID uuid;

    @JsonView(View.Basic.class)
    @NotNull
    @Embedded
    private PersonalDataDto personalDatadto;

    @JsonView(View.Extended.class)
    @Nullable
    private List<DeliveryAddressDto> deliveryAddressDtos;

    @JsonView(View.Extended.class)
    @NotNull
    @Embedded
    private LogginDataDto logginDataDto;

    @JsonIgnore
    @Nullable
    private List<OrderDto> orderDtos;

    @JsonView(View.Extended.class)
    @NotNull
    private List<OperationEvidenceDto> operationEvidenceDtos;

    @JsonView(View.Extended.class)
    @Nullable
    private List<DiscountCodeDto> discountCodeDtos;

    @JsonView(View.Extended.class)
    @NotNull
    @Enumerated(EnumType.STRING)
    private Archive archive;
}
