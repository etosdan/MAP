package hr.vsite.ulaznitestovi;

public class User {
    private String userId;
    private String name;
    private String surname;
    private String email;
    private String university;
    private String password;
    private Role role;

    public User(String name, String surname, String email, String university, String password, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.university = university;
        this.password = password;
        this.role = role;

    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {return role; }

    public void setRole(Role role) { this.role = role; }
}
