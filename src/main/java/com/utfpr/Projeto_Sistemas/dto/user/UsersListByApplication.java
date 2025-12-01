package com.utfpr.Projeto_Sistemas.dto.user;

import java.util.List;

public record UsersListByApplication(
        List<UserJobDto> items
) {
}
