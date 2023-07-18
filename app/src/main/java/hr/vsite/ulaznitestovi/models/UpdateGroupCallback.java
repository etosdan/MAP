package hr.vsite.ulaznitestovi.models;

public interface UpdateGroupCallback extends FailureCallback {
    void onGroupUpdated(Group updatedGroup);
}
