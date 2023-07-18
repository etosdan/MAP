package hr.vsite.ulaznitestovi.models;

public interface TestSave {
    void onTestSaved(String testId);

    void onFailure(String errorMessage);
}
