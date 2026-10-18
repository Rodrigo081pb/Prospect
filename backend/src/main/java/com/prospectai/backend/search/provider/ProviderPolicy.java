package com.prospectai.backend.search.provider;

public record ProviderPolicy(
        boolean canStore,
        boolean canExport,
        String attribution
) { }
