package com.tyrontundrom.eatforit.model;

import com.tyrontundrom.eatforit.model.enums.Sex;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import javax.annotation.Nullable;

@Embeddable
class PersonalData {

    @Nullable
    private String name;

    @Nullable
    private String surname;

    @Nullable
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Nullable
    private String phone;

    @Nullable
    private String email;
}
