package hr.vsite.ulaznitestovi.models;

public interface TestDelete {
    void onTestDeleted();

    void onFailure(String errorMessage);
}
