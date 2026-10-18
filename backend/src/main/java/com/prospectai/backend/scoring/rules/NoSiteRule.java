package com.prospectai.backend.scoring.rules;

import com.prospectai.backend.scoring.domain.Reason;
import com.prospectai.backend.scoring.domain.ReasonType;
import com.prospectai.backend.search.domain.Business;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.util.Optional;

@Component
public class NoSiteRule implements ScoreRule {

    @Override
    public Optional<Reason> evaluate(Business business){
        // se não encontramos site na fonte, é um dado ausente não que existe
        if(business.websiteUrl() == null || business.websiteUrl().isBlank()){
            return Optional.of(new Reason(
                    ReasonType.MISSING_DATA,
                    "Nenhum site encontrado na consulta",
                    35
                    ));
        }
        return Optional.empty();
    }

}
