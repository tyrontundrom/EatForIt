package com.tyrontundrom.eatforit.events;

import com.tyrontundrom.eatforit.dto.UserDto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public
class OperationEvidenceCreator extends ApplicationEvent {

    private final UserDto userDto;
    public OperationEvidenceCreator(Object source, UserDto userDto) {
        super(source);
        this.userDto = userDto;
    }
}
