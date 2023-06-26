package com.tyrontundrom.eatforit.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.google.common.base.Joiner;
import com.tyrontundrom.eatforit.model.enums.Sex;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import javax.annotation.Nullable;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
class PersonalDataDto {

    public static class View {
        public interface Basic {}
        public interface Extended extends Basic {}
    }

    @JsonView(View.Basic.class)
    @Nullable
    private String name;

    @JsonView(View.Basic.class)
    @Nullable
    private String surname;

    @JsonView(View.Extended.class)
    @Nullable
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @JsonView(View.Extended.class)
    @Nullable
    private String phone;

    @JsonView(View.Extended.class)
    @Nullable
    private String email;

    @JsonView(View.Basic.class)
    public String nameAndSurname() {
        return Joiner.on(" ").skipNulls().join(name, surname);
    }
}
