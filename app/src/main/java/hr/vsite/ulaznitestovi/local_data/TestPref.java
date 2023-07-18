package hr.vsite.ulaznitestovi.local_data;

import android.content.Context;
import android.content.SharedPreferences;

import hr.vsite.ulaznitestovi.helpers.Constants;


public class TestPref {
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static TestPref testPref;

    private TestPref(Context context) {
        sharedPreferences = context.getSharedPreferences("shared", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public static void init(Context context) {
        if (testPref == null) {
            testPref = new TestPref(context);
        }
    }

    public static TestPref getInstance() {
        return testPref;
    }

    public void saveName(String name) {
        editor.putString("name", name);
        editor.apply();
    }

    public void historyQuiz(String quizList) {
        editor.putString("historys", quizList);
        editor.apply();
    }

    public void shoResultQuiz(String quizList) {
        editor.putString("showResultQuiz", quizList);
        editor.apply();
    }

    public String getShowResultQuiz() {
        return sharedPreferences.getString("showResultQuiz", "");
    }

    public String getHistoryQuiz() {
        return sharedPreferences.getString("historys", "");
    }

    public String getName() {
        return sharedPreferences.getString("name", "");
    }

    public void savePoint(int point) {
        editor.putInt(Constants.POINT_KEY, point);
        editor.apply();
    }

    public int getPoint() {
        return sharedPreferences.getInt(Constants.POINT_KEY, 0);
    }

}
