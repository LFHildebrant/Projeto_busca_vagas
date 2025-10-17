package com.utfpr.Projeto_Sistemas.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@Setter
@AllArgsConstructor
public class FieldMessage {

    private String field;
    private String error;

    public FieldMessage() {
    }
}
