package com.utfpr.Projeto_Sistemas.dto.job;

import java.util.List;

public record AllApplJobsByUser(
        List<JobApplUser> items
) {
}
