package hr.vsite.ulaznitestovi.result;

public class Result {
    private String userName;
    private String resultStatus;

    public Result(String userName, String resultStatus) {
        this.userName = userName;
        this.resultStatus = resultStatus;
    }

    public String getUserName() {
        return userName;
    }

    public String getResultStatus() {
        return resultStatus;
    }
}
