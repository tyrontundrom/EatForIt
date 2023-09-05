package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.Null;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.annotation.Nullable;
import java.util.List;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DelivererDto extends EmployeeDto {

    public static class View {
        public interface Id extends EmployeeDto.View.Id {}
        public interface Basic extends EmployeeDto.View.Basic{}
        public interface Extended extends Basic, EmployeeDto.View.Extended {}
    }

    public interface NewDelivererValidation {}

    @JsonView(View.Extended.class)
    @Nullable
    @Null(groups = NewDelivererValidation.class)
    private List<OrderDto> orders;

   }
