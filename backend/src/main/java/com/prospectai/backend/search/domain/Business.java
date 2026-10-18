package com.prospectai.backend.search.domain;

import java.util.Set;

public record Business(
        String id,
        String externalId,
        String source,
        String name,
        String category,
        String address,
        String phone,
        String websiteUrl,
        Double latitude,
        Double longitude,
        Set<String> sourceFlags
) {}