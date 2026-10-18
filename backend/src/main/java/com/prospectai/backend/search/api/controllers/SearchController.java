package com.prospectai.backend.search.api.controllers;

import com.prospectai.backend.search.api.requests.SearchRequest;
import com.prospectai.backend.search.domain.SearchSession;
import com.prospectai.backend.search.service.SearchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/searches")
public class SearchController {

    private final SearchService service;

    public SearchController(SearchService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SearchSession create(@Valid @RequestBody SearchRequest req) {
        int limit = req.limit() == null ? 20 : req.limit();
        return service.create(req.city().trim(), req.niche().trim(), limit);
    }

    @GetMapping("{id}")
    public SearchSession get(@PathVariable String id) {
        return service.get(id);
    }

}
