package com.prospectai.backend.scoring.service;

import com.prospectai.backend.scoring.domain.Priority;
import com.prospectai.backend.scoring.domain.Reason;
import com.prospectai.backend.scoring.domain.ScoredLead;
import com.prospectai.backend.scoring.rules.ScoreRule;
import com.prospectai.backend.search.domain.Business;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoringService {

    // o Spring injeta automaticamente todas as classes que implementam ScoreRule
    private final List<ScoreRule> rules;

    public ScoringService(List<ScoreRule> rules) {
        this.rules = rules;
    }

    public ScoredLead score(Business business){
        // roda todas as regras e coleta os motivos gerados
        List<Reason> reasons = rules.stream()
                .map(rule -> rule.evaluate(business))
                .filter(opt -> opt.isPresent())
                .map(opt -> opt.get())
                .toList();

        // soma os pesos de todos os motivos
        int raw = reasons.stream()
                .mapToInt(Reason::weight)
                .sum();

        // limita o score entre 0 e 100
        int score = Math.max(0, Math.min(100, raw));

        // define a prioridade por faixa
        Priority priority = resolvePriority(score, reasons);

        return new ScoredLead(business, score, priority, reasons);
    }

    public List<ScoredLead> scoreAll(List<Business> businesses){
        return businesses.stream()
                .map(this::score)
                // ordena do maior score para o menor
                .sorted((a, b) -> Integer.compare(b.score(), a.score()))
                .toList();
    }

    private Priority resolvePriority(int score, List<Reason> reasons){
        // se não gerou nenum motivo, dados insuficientes
        if(reasons.isEmpty()) return Priority.REVIEW;
        if(score >= 65) return Priority.HIGH;
        if(score >= 40) return Priority.MEDIUM;
        return Priority.LOW;
    }

}
