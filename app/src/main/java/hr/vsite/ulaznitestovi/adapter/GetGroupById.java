package hr.vsite.ulaznitestovi.adapter;

import hr.vsite.ulaznitestovi.models.Group;

public interface GetGroupById extends FailureCallback{
    void onGroupRetrieved(Group retrievedGroup);

}
