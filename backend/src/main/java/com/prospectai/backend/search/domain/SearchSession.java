package com.prospectai.backend.search.domain;

import com.prospectai.backend.scoring.domain.ScoredLead;

import java.time.Instant;
import java.util.List;

public record SearchSession(
        String id,
        String city,
        String niche,
        SearchStatus status,
        List<ScoredLead> businesses,
        Instant createdAt,
        String attribution
) {}