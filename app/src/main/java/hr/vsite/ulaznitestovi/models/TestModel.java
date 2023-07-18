package hr.vsite.ulaznitestovi.models;

public class TestModel {
    private final Integer txtQuestion;//Qozonda emas Qaynaydi qishin yozin tinmaydi !
    private final String answer;//Buloq
    private final String variant;//baulgdqos

    public TestModel(int txtQuestion, String answer, String variant) {
        this.txtQuestion = txtQuestion;
        this.answer = answer;
        this.variant = variant;
    }

    public Integer getTxtQuestion() {
        return txtQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public String getVariant() {
        return variant;
    }
}
