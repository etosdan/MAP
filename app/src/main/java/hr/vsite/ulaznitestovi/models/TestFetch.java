package hr.vsite.ulaznitestovi.models;

import hr.vsite.ulaznitestovi.models.Test;

public interface TestFetch {
    void onTestFetched(Test test);

    void onFailure(String errorMessage);
}

