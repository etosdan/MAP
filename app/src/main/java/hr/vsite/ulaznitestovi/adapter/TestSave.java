package hr.vsite.ulaznitestovi.adapter;

public interface TestSave {
    void onTestSaved(String testId);

    void onFailure(String errorMessage);
}
