package hr.vsite.ulaznitestovi.models;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private String groupId;
    private String groupName;
    private String university;

    private String authorId;
    private List<String> testIds;
    private List<String> userIds;

    public Group() {
        // Default constructor required for Firestore serialization
    }

    public Group(String groupName, String university, String authorId, List<String> userIds) {
        this.groupName = groupName;
        this.university = university;
        this.authorId = authorId;
        this.testIds = new ArrayList<>(); // Initialize as an empty list
        this.userIds = userIds;
    }


    public Group(String groupName) {
        this.groupName=groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public List<String> getTestIds() {
        return testIds;
    }

    public void setTestIds(List<String> testIds) {
        this.testIds = testIds;
    }

    public List<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<String> userIds) {
        this.userIds = userIds;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}

