package hr.vsite.ulaznitestovi.models;


import hr.vsite.ulaznitestovi.contract.MainContract;
import hr.vsite.ulaznitestovi.repository.AppRepository;

public class MainModel implements MainContract.MainModel {
    private final AppRepository repository;

    public MainModel() {
        repository = AppRepository.getInstance();

    }
}
