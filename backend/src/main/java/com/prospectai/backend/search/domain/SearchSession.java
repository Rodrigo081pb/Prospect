package com.prospectai.backend.search.domain;

import java.time.Instant;
import java.util.List;

public record SearchSession(
        String id,
        String city,
        String niche,
        SearchStatus status,
        List<Business> businesses,
        Instant createdAt,
        String attribution
) {}