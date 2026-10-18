package com.prospectai.backend.search.api.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record SearchRequest(
        @NotBlank String city,
        @NotBlank String niche,
        @Min(1) @Max(100) Integer limit
) { }
