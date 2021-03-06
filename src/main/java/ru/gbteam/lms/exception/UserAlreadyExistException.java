package ru.gbteam.lms.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserAlreadyExistException extends RuntimeException{

    private String message;
    private String username;

    public UserAlreadyExistException(String message, String username) {
        this.message = message;
        this.username = username;
    }
}
