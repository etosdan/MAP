package hr.vsite.ulaznitestovi.adapter;

import java.util.List;

import hr.vsite.ulaznitestovi.models.Group;

public interface GroupFetch {
    void onGroupsFetched(List<Group> groups);

    void onFailure(String errorMessage);
}
