package com.prospectai.backend.scoring.domain;

public enum Priority {
    HIGH,    // socore >= 65 : vale abordar
    MEDIUM,  // score 40-64 : vale investigar
    LOW,     // socore < 40: menos urgente
    REVIEW   // dados insuficientes para determinar prioridade.
}
