package com.prospectai.backend.scoring.domain;

public enum ReasonType {
    FACT, // Algo que verificamos e é verdade
    MISSING_DATA, // algo que não encontramos na fonte dos dados
    ESTIMATE // uma estimativa baseada em indicios
}
