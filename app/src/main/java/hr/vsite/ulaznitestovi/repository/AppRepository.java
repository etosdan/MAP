package hr.vsite.ulaznitestovi.repository;


import hr.vsite.ulaznitestovi.local_data.source.LocalPref;

public class AppRepository {
    private static AppRepository appRepository;
    private final LocalPref appRef;

    private AppRepository() {
        appRef = LocalPref.getInstance();
    }

    public static AppRepository getInstance() {
        return appRepository = new AppRepository();
    }

    public void savePosition(int position) {
        appRef.setSavePosition(position);
    }

    public int getPosition() {
        return appRef.getPosition();
    }
}
