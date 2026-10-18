package com.prospectai.backend.search.provider;

import com.prospectai.backend.search.domain.Business;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class FakeProvider implements BusinessProvider {

    @Override
    public String name() { return "fake"; }

    @Override
    public ProviderPolicy policy() {
        return new ProviderPolicy(true, true, null);
    }

    @Override
    public List<Business> search(String city, String niche, int limit) {
        List<Business> result = new ArrayList<>();
        for (int i = 1; i <= limit; i++) {
            boolean hasSite = i % 3 == 0;
            boolean hasPhone = i % 4 != 0;
            result.add(new Business(
                    "fake-" + i,
                    "fake/" + i,
                    "fake",
                    capitalize(niche) + " Exemplo " + i,
                    niche,
                    "Rua Teste, " + (100 + i) + " - " + city,
                    hasPhone ? "(81) 9" + String.format("%04d", 1000 + i) + "-0000" : null,
                    hasSite ? "https://exemplo" + i + ".com.br" : null,
                    -8.05 + i * 0.001,
                    -34.88 + i * 0.001,
                    Set.of("name", "address")
            ));
        }
        return result;
    }

    private String capitalize(String s) {
        return s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}