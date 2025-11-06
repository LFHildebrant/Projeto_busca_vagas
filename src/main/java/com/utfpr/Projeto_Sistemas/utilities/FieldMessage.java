package com.utfpr.Projeto_Sistemas.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@Setter
@AllArgsConstructor
public class FieldMessage {

    private Integer Status;
    private String field;
    private Object error;
    private String message;

    public FieldMessage() {
    }
    public FieldMessage(String field,  String message) {
        this.field = field;
        this.message = message;
    }
}
