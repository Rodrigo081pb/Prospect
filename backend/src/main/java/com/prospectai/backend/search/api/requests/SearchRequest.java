
package com.prospectai.backend.search.api.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "SearchRequest",
        description = "Dados necessários para iniciar uma busca de empresas."
)
public record SearchRequest(

        @Schema(
                description = "Cidade onde as empresas serão pesquisadas.",
                example = "Recife",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "A cidade é obrigatória e não pode estar vazia.")
        String city,

        @Schema(
                description = "Nicho de empresas que desejamos encontrar.",
                example = "barbearia",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank(message = "O nicho é obrigatório e não pode estar vazio.")
        String niche,

        @Schema(
                description = "Quantidade máxima de empresas a retornar. Padrão: 20. Mínimo: 1. Máximo: 100.",
                example = "20",
                minimum = "1",
                maximum = "100"
        )
        @Min(value = 1, message = "O limite mínimo é 1.")
        @Max(value = 100, message = "O limite máximo é 100.")
        Integer limit

) { }
