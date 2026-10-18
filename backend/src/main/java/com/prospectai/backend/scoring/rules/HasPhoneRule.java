package com.prospectai.backend.scoring.rules;

import com.prospectai.backend.scoring.domain.Reason;
import com.prospectai.backend.scoring.domain.ReasonType;
import com.prospectai.backend.search.domain.Business;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HasPhoneRule implements ScoreRule{

    @Override
    public Optional<Reason> evaluate(Business business){
        // telefone disponível facilita o contato: bom sinal de negócio ativo
        if(business.phone() != null && !business.phone().isBlank()){
            return Optional.of(new Reason(
                    ReasonType.FACT,
                    "Telefone disponível para contato",
                    10
            ));
        }
        // sem telefone dificulta a abordagem: penaliza levemente
        return Optional.of(new Reason(
                ReasonType.MISSING_DATA,
                "Nenhum telefone encontrado na consulta",
                -10
        ));
    }

}
