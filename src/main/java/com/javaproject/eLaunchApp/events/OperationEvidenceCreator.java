package com.javaproject.eLaunchApp.events;

import com.javaproject.eLaunchApp.DTO.UserDTO;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

public class OperationEvidenceCreator extends ApplicationEvent {

    private final UserDTO userDTO;

    public OperationEvidenceCreator(Object source, UserDTO userDTO) {
        super(source);
        this.userDTO = userDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }
}
