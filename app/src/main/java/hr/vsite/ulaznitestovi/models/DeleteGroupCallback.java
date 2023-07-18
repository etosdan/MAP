package hr.vsite.ulaznitestovi.models;

public interface DeleteGroupCallback extends FailureCallback {
    void onGroupDeleted(Group deletedGroup);
}
