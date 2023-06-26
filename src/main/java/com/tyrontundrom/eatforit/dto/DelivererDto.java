package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

import javax.annotation.Nullable;
import java.util.List;

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

    @JsonView(View.Extended.class)
    @Nullable
    private List<OrderDto> orders;

   }
