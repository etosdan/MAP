package hr.vsite.ulaznitestovi.models;

public interface CreateGroupCallback extends FailureCallback {
    void onCreateGroup(String groupId);
}
