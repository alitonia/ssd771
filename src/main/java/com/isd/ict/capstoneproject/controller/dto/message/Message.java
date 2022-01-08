package com.isd.ict.capstoneproject.controller.dto.message;

import lombok.Getter;
/**
 * The {@link Message message}.
 *
 *
 */
@Getter
public abstract class Message {
    /**
     * message
     */
    private final String message;
    /**
     * result
     */
    private final Object result;

    public Message(String message, Object result) {
        this.message = message;
        this.result = result;
    }
}
