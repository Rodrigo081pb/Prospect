package com.prospectai.backend.scoring.domain;

import com.prospectai.backend.search.domain.Business;

import java.util.List;

public record ScoredLead(
        Business business, // dados da empresa
        int score, // score final da empresa de 0 a 100
        Priority priority, // prioridade da empresa
        List<Reason> reasons // motivos que contribuíram para o score
) { }
