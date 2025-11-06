package com.utfpr.Projeto_Sistemas.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FieldError {
    private String field;
    private String error;
}
