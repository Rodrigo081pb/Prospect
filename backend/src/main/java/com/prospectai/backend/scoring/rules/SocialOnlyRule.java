package com.prospectai.backend.scoring.rules;

import com.prospectai.backend.scoring.domain.Reason;
import com.prospectai.backend.scoring.domain.ReasonType;
import com.prospectai.backend.search.domain.Business;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SocialOnlyRule implements ScoreRule {

    // dominios que indicam que o "site" é só uma rede social
    private static final java.util.List<String> SOCIAL_DOMAINS = java.util.List.of(
        "facebook.com",
        "wa.me",
        "linktree.com",
        "instagram.com",
        "linkedin.com",
        "twitter.com",
        "tiktok.com",
        "youtube.com"
    );

    @Override
    public Optional<Reason> evaluate(Business business) {
        String url = business.websiteUrl();
        if(url == null) return Optional.empty();

        // verifica se a URL contém dominio de rede social
        boolean isSocial = SOCIAL_DOMAINS.stream()
                .anyMatch(domain -> url.toLowerCase().contains(domain));

        if(isSocial){
            return Optional.of(new Reason(
                    ReasonType.MISSING_DATA,
                    "O site encontrado é apenas uma rede social",
                    30
            ));
        }
        return Optional.empty();
    }

}
