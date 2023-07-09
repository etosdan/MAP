package hr.vsite.ulaznitestovi.adapter;

import hr.vsite.ulaznitestovi.models.Group;

public interface DeleteGroupCallback extends FailureCallback {
    void onGroupDeleted(Group deletedGroup);
}
