package com.tyrontundrom.eatforit.dto;

import lombok.*;

import javax.annotation.Nullable;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class DelivererDto extends EmployeeDto {

    @Nullable
    private List<OrderDto> orders;

   }
