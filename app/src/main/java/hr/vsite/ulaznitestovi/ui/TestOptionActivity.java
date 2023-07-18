package hr.vsite.ulaznitestovi.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import hr.vsite.ulaznitestovi.R;
import hr.vsite.ulaznitestovi.helpers.Constants;
import hr.vsite.ulaznitestovi.ui.page.GeographyOrLiteratureQuizActivity;
import hr.vsite.ulaznitestovi.ui.page.MathQuizActivity;

public class TestOptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_option);
        CardView cvMath = findViewById(R.id.cvMath);
        findViewById(R.id.imageViewQuizOption).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        cvMath.setOnClickListener(view -> {
            mathClick();
        });
        CardView cvGeoGraph = findViewById(R.id.cvGeography);
        cvGeoGraph.setOnClickListener(view -> {
        });
        CardView cvLiterature = findViewById(R.id.cvLiterature);
        cvLiterature.setOnClickListener(view -> {
            literatureClick();
        });
    }

    public void mathClick() {
        Intent intent = new Intent(TestOptionActivity.this, MathQuizActivity.class);
        intent.putExtra(Constants.SUBJECT, getString(R.string.geography));
        startActivity(intent);

    }

    public void literatureClick() {
        Intent intent = new Intent(TestOptionActivity.this, GeographyOrLiteratureQuizActivity.class);
        intent.putExtra(Constants.SUBJECT, getString(R.string.literature));
        startActivity(intent);
    }
}