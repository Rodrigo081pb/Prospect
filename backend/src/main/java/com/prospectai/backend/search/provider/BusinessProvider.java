package com.prospectai.backend.search.provider;

import com.prospectai.backend.search.domain.Business;

import java.util.List;

public interface BusinessProvider {

    String name();
    List<Business> search(String city, String niche, int limit);
    ProviderPolicy policy();

}
