package com.prospectai.backend.search.api.controllers;

import com.prospectai.backend.search.api.requests.SearchRequest;
import com.prospectai.backend.search.domain.SearchSession;
import com.prospectai.backend.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/searches")
@Tag(
        name = "Buscas",
        description = "Endpoints para criar e consultar buscas."
)
public class SearchController {

    private final SearchService service;

    /**
     * Injeção de dependência via construtor
     */
    public SearchController(SearchService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Cria uma nova busca",
            description = "Cria uma nova busca com base na cidade e nicho fornecidos."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Busca criada com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "Dados inválidos"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor"
            )
    })
    public SearchSession create(@Valid @RequestBody SearchRequest req) {
        // Remove espaços desnecessários e aplica o limite padrão.
        int limit = req.limit() == null ? 20 : req.limit();
        return service.create(req.city().trim(), req.niche().trim(), limit);
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Consulta uma busca existente",
            description = "Consulta uma busca existente pelo seu ID."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Busca encontrada com sucesso"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "Busca não encontrada"
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor"
            )
    })
    public SearchSession get(
            @Parameter(
                    description = "ID da busca a ser consultada",
                    required = true
            )
            @PathVariable String id
    ) {
        return service.get(id);
    }

}
