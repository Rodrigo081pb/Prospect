package com.prospectai.backend.scoring.domain;

public record Reason(
        ReasonType type, // FACT, MISSING_DATA, ESTIMATE
        String text, // texto explicativo do motivo
        int weight // peso que esse motivo contribuiu no score
) {
}
