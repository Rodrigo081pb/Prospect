package com.prospectai.backend.scoring.rules;

import com.prospectai.backend.scoring.domain.Reason;
import com.prospectai.backend.search.domain.Business;

import java.util.Optional;

public interface ScoreRule {
    Optional<Reason> evaluate(Business business);
}
