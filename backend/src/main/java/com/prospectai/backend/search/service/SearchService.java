package com.prospectai.backend.search.service;

import com.prospectai.backend.scoring.domain.ScoredLead;
import com.prospectai.backend.scoring.service.ScoringService;
import com.prospectai.backend.search.domain.Business;
import com.prospectai.backend.search.domain.SearchSession;
import com.prospectai.backend.search.domain.SearchStatus;
import com.prospectai.backend.search.provider.BusinessProvider;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SearchService {

    private final BusinessProvider provider;
    private final ScoringService scoring;
    private final Map<String, SearchSession> sessions = new ConcurrentHashMap<>();

    public SearchService(BusinessProvider provider, ScoringService scoring) {
        this.provider = provider;
        this.scoring = scoring;
    }

    public SearchSession create(String city, String niche, int limit) {

        // busca as empresas no provider
        var businesses = provider.search(city, niche, limit);

        // aplica o score em todas
        List<ScoredLead> leads = scoring.scoreAll(businesses);

        String id = UUID.randomUUID().toString().substring(0, 8);

        SearchSession session = new SearchSession(
                id,
                city,
                niche,
                SearchStatus.DONE,
                leads,
                Instant.now(),
                provider.policy().attribution()
        );
        sessions.put(id, session);
        return session;
    }

    public SearchSession get(String id) {
        SearchSession s = sessions.get(id);
        if (s == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pesquisa não encontrada");
        }
        return s;
    }

}
