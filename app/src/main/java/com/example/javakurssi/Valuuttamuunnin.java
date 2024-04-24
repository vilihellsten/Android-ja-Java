package com.example.javakurssi;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;

public class Valuuttamuunnin extends AppCompatActivity {

    private MaterialButtonToggleGroup materialButtonToggleGroup;

    private String error = "Valuutan muuntaminen ei onnistu\nKäytä omaa desimaalierotintasi";
    private TextView raha;

    private TextInputLayout euro;

    private double sek = 0.09968;

    private double nok = 0.10288;

    private double dkk = 0.13440;

    /*1 SEK = 0.09968 EUR
    • 1 NOK = 0.10288 EUR
    • 1 DKK = 0.13440 EUR*/
    private String muunto;

    private double muunto2;

    DecimalFormat decimalFormat = new DecimalFormat("#.000");

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_valuuttamuunnin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        toolbar = (Toolbar) findViewById(R.id.gameToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        materialButtonToggleGroup = findViewById(R.id.toggle);

        materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.sek) {
                        Log.e("raha", "sek");
                        calculateSek();
                    } else if (checkedId == R.id.nok) {
                        Log.e("raha", "nok");
                        calculateNok();
                    } else if (checkedId == R.id.ddk) {
                        Log.e("raha", "ddk");
                        calculateDdk();
                    }
                }
            }
        });

        raha = (TextView) findViewById(R.id.raha);

        euro = (TextInputLayout) findViewById(R.id.euro);
        euro.setError("");
    }

    public void calculateSek() {
        euro.setError(null);
        muunto = String.valueOf(euro.getEditText().getText());
        Log.e("muunto",muunto);
        try {
            muunto2 = Double.parseDouble(muunto);
            raha.setText(String.valueOf(decimalFormat.format(muunto2 * sek) + " €"));
        }catch (Exception e){
            euro.setError(error);
        }
    }
    public void calculateNok(){
        euro.setError(null);
        muunto = String.valueOf(euro.getEditText().getText());
        Log.e("muunto",muunto);
        try {
            muunto2 = Double.parseDouble(muunto);
            raha.setText(String.valueOf(decimalFormat.format(muunto2 * nok) + " €"));
        }catch (Exception e){
            euro.setError(error);
        }
    }

    public void calculateDdk(){
        euro.setError(null);
        muunto = String.valueOf(euro.getEditText().getText());
        Log.e("muunto",muunto);
        try {
            muunto2 = Double.parseDouble(muunto);
            raha.setText(String.valueOf(decimalFormat.format(muunto2 * dkk) + " €"));
        }catch (Exception e){
            euro.setError(error);
        }
    }
}