package com.prospectai.backend.search.service;

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
    private final Map<String, SearchSession> sessions = new ConcurrentHashMap<>();

    public SearchService(BusinessProvider provider) {
        this.provider = provider;
    }

    public SearchSession create(String city, String niche, int limit) {
        List<Business> found = provider.search(city, niche, limit);
        String id = UUID.randomUUID().toString().substring(0, 8);
        SearchSession session = new SearchSession(
                id,
                city,
                niche,
                SearchStatus.DONE,
                found,
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
