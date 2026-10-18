package com.prospectai.backend.scoring.rules;

import com.prospectai.backend.scoring.domain.Reason;
import com.prospectai.backend.scoring.domain.ReasonType;
import com.prospectai.backend.search.domain.Business;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HasInstagramRule  implements ScoreRule{

    @Override
    public Optional<Reason> evaluate(Business business){
        String url = business.websiteUrl();
        if(url == null) return Optional.empty();

        // tem instagram mas não tem site próprio
        boolean hasInstagram = url.toLowerCase().contains("instagram.com");
        boolean hasOwnSite = !url.isBlank();

        if(hasInstagram && !hasOwnSite){
            return Optional.of(new Reason(
                    ReasonType.ESTIMATE,
                    "O site encontrado é apenas uma rede social (Instagram)",
                    15
            ));
        }

        return Optional.empty();

    }

}
