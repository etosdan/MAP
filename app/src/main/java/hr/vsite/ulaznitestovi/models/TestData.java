package hr.vsite.ulaznitestovi.models;

public class TestData {
    String chooses;
    boolean type;

    public TestData(String chooses, boolean type) {
        this.chooses = chooses;
        this.type = type;
    }

    public String getChooses() {
        return chooses;
    }

    public void setChooses(String chooses) {
        this.chooses = chooses;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }
}
