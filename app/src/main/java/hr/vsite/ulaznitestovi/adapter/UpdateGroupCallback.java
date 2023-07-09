package hr.vsite.ulaznitestovi.adapter;

import hr.vsite.ulaznitestovi.models.Group;

public interface UpdateGroupCallback extends FailureCallback {
    void onGroupUpdated(Group updatedGroup);
}
