package hr.vsite.ulaznitestovi.models;

public interface GetGroupById extends FailureCallback {
    void onGroupRetrieved(Group retrievedGroup);

}
