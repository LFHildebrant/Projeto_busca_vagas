package com.utfpr.Projeto_Sistemas.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ValidationErrorMessage {

    private String message;
    private String code;
    private List<FieldMessage> details;

    public ValidationErrorMessage() {
    }
}


