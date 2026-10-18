package com.prospectai.backend.search.tests;

import com.prospectai.backend.search.api.requests.SearchRequest;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchRequestTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    @BeforeAll
    static void setUp(){
        // Inicializa o meccanismo de validação apenas uma vez para os testes
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown(){
        // Fecha o factory após todos os testes
        factory.close();
    }

    @Test
    void shouldAcceptValidRequest(){
        // Arrange : Cria uma requisição com dados válidos
        SearchRequest request = new SearchRequest(
                "Recife",
                "barbearia",
                20
        );

        // Act : Executa as validações declaradas no record
        var violations = validator.validate(request);

        // Assert : Verifica se não há violações de validação
        assertTrue(violations.isEmpty());

    }


    @Test
    void shouldRejectBlankCity(){
        // Arrange : Cria uma requisição com cidade em branco
        SearchRequest request = new SearchRequest(
                "   ",  // Cidade em branco
                "barbearia",
                20
        );

        // Act : Executa as validações declaradas no record
        var violations = validator.validate(request);

        // Assert : Verifica se há violações de validação
        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("city") && v.getMessage().contains("obrigatória"))
        );
    }

    @Test
    void shouldRejectBlankNiche(){
        // Arrange : Cria uma requisição com nicho em branco
        SearchRequest request = new SearchRequest(
                "Recife",
                "   ",  // Nicho em branco
                20
        );

        // Act : Executa as validações declaradas no record
        var violations = validator.validate(request);

        // Assert : Verifica se há violações de validação
        assertTrue(
                violations.stream()
                        .anyMatch(violation ->
                                violation.getMessage().equals("O nicho é obrigatório e não pode estar vazio.")
                                )
        );
    }

    @Test
    void shouldRejectLimitBelowMinimum(){
        // Arrange : Cria uma requisição com limite abaixo do mínimo
        SearchRequest request = new SearchRequest(
                "Recife",
                "barbearia",
                0  // Limite abaixo do mínimo
        );

        // Act : Executa as validações declaradas no record
        var violations = validator.validate(request);

        // Assert : Verifica se há violações de validação
        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("limit") && v.getMessage().contains("mínimo"))
        );
    }

    @Test
    void shouldRejectLimitAboveMaximum(){
        // Arrange : Cria uma requisição com limite acima do máximo
        SearchRequest request = new SearchRequest(
                "Recife",
                "barbearia",
                101  // Limite acima do máximo
        );

        // Act : Executa as validações declaradas no record
        var violations = validator.validate(request);

        // Assert : Verifica se há violações de validação
        assertTrue(
                violations.stream()
                        .anyMatch(v -> v.getPropertyPath().toString().equals("limit") && v.getMessage().contains("máximo"))
        );
    }

    @Test
    void shouldAcceptNullLimit(){
        // Arrange : Cria uma requisição com limite nulo
        SearchRequest request = new SearchRequest(
                "Recife",
                "barbearia",
                null  // Limite nulo
        );

        // Act : Executa as validações declaradas no record
        var violations = validator.validate(request);

        // Assert : Verifica se não há violações de validação
        assertTrue(violations.isEmpty());
    }


}
