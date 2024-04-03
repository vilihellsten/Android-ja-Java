package com.example.javakurssi;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

import io.reactivex.Single;


public class GameActivity extends AppCompatActivity {

    RxDataStore<Preferences> dataStoreRX;
    Random rand = new Random();
    int random = rand.nextInt(4);

    private static final String KEY_HS = "HighestScore";

    private static final String TAG = "GameActivity";
    // github skzeeshan365 reiserx // src main java com data store ja datastore helper
    private ImageButton button;
    private ImageButton button2;
    private ImageButton button3;
    private ImageButton button4;
    private int flip;
    private TextView textView;
    private TextView textView1;

    private int counter;
    private Preferences pref_error;

    private Toolbar toolbar;
    private int highestSuccessCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataStoreRX = new RxPreferenceDataStoreBuilder(this, "name_for_datastore").build();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            return insets;


        });

        toolbar = (Toolbar) findViewById(R.id.gameToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        /*
        tämä oli uudessa luookassa
        public DataStoreHelper(){ this.dataStoreRX ? DataStore.getInstance().getDataStore();}

        DataStoreSingleton dataStoreSingleton = DataStoreSingleton.getInstance();
        if (dataStoreSingleton.getDataStore() == null) {
            dataStoreRX = new RxPreferenceDataStoreBuilder(this, TAG_STORE_NAME).build();
        } else {
            dataStoreRX = dataStoreSingleton.getDataStore();
        }
        dataStoreSingleton.setDataStore(dataStoreRX);
        dataStoreHelper = new DataStoreHelper();

        ylös vielä RX?
        kokeile ilman classia

        tällä muistiin
        dataStoreHelper.putStringValue(KEY_HS, STring.valueOf(asdasd)

        haku
        String asdasd = dataStoreHelper.getStringValue(KEY_HS; def "0"

        string intiks
        asdasd = Integer.valueOf(asdasd)
        */

        flip = 1;
        textView = (TextView) findViewById(R.id.Highscore);
        textView.setText(String.valueOf("Guess highscore " + highestSuccessCount));

        textView1 = (TextView) findViewById(R.id.textView3);

        button = (ImageButton) findViewById(R.id.button);
        button2 = (ImageButton) findViewById(R.id.button2);
        button3 = (ImageButton) findViewById(R.id.button3);
        button4 = (ImageButton) findViewById(R.id.button4);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buttonanimation);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override // Reset game on clicking floating action button
            public void onClick(View view) {
                clearAnimations();
                Log.d(TAG, String.valueOf(random));
                random = rand.nextInt(4);
                resetPictures();
                counter = 0;
                textView1.setText(String.valueOf(counter));
                flip = 1;
            }
        });

    }
    public boolean putStringValue(String Key, String value){ // TEE OMAT JAVAKLASSIT
        boolean returnvalue;
        Preferences.Key<String> PREF_KEY = PreferencesKeys.stringKey(Key);
        Single<Preferences> updateResult =  dataStoreRX.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(PREF_KEY, value);
            return Single.just(mutablePreferences);
        }).onErrorReturnItem(null);

        returnvalue = updateResult.blockingGet() != pref_error;
        return returnvalue;
    }

    String getStringValue(String Key) {
        Preferences.Key<String> PREF_KEY = PreferencesKeys.stringKey(Key);
        Single<String> value = dataStoreRX.data().firstOrError().map(prefs -> prefs.get(PREF_KEY)).onErrorReturnItem("null");
        return value.blockingGet();
    }

    public void clearAnimations() {
        button.clearAnimation();
        button2.clearAnimation();
        button3.clearAnimation();
        button4.clearAnimation();
    }

    public void resetPictures() {
        button.setImageResource(android.R.drawable.btn_star_big_on);
        button2.setImageResource(android.R.drawable.btn_star_big_on);
        button3.setImageResource(android.R.drawable.btn_star_big_on);
        button4.setImageResource(android.R.drawable.btn_star_big_on);
    }

    public void calculateHighscore() {
        if (counter == 1) {
            highestSuccessCount += 4;
            textView.setText(String.valueOf("Guess highscore " + highestSuccessCount));

        }
        if (counter == 2) {
            highestSuccessCount += 3;
            textView.setText(String.valueOf("Guess highscore " + highestSuccessCount));

        }
        if (counter == 3) {
            highestSuccessCount += 2;
            textView.setText(String.valueOf("Guess highscore " + highestSuccessCount));

        }
        if (counter == 4) {
            highestSuccessCount += 1;
            textView.setText(String.valueOf("Guess highscore " + highestSuccessCount));

        }
    }


    public void handleOnClickEvents(View v) {
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buttonanimation);

        if (flip == 1) {
            switch (v.getId()) {

                case R.id.button:
                    if (counter < 4) {
                        counter = counter + 1;
                        textView1.setText(String.valueOf(counter));
                    }
                    button.startAnimation(animation);
                    if (random == 0) {
                        button.setImageResource(R.mipmap.ic_launcher);
                        flip = 2;
                        calculateHighscore();
                    }
                    break;

                case R.id.button2:
                    if (counter < 4) {
                        counter = counter + 1;
                        textView1.setText(String.valueOf(counter));
                    }
                    button2.startAnimation(animation);
                    if (random == 1) {
                        button2.setImageResource(R.mipmap.ic_launcher);
                        flip = 2;
                        calculateHighscore();
                    }
                    break;

                case R.id.button3:
                    if (counter < 4) {
                        counter = counter + 1;
                        textView1.setText(String.valueOf(counter));
                    }
                    button3.startAnimation(animation);
                    if (random == 2) {
                        button3.setImageResource(R.mipmap.ic_launcher);
                        flip = 2;
                        calculateHighscore();
                    }
                    break;

                case R.id.button4:
                    if (counter < 4) {
                        counter = counter + 1;
                        textView1.setText(String.valueOf(counter));
                    }
                    button4.startAnimation(animation);
                    if (random == 3) {
                        button4.setImageResource(R.mipmap.ic_launcher);
                        flip = 2;
                        calculateHighscore();
                    }
                    break;
            }
        }
    }



}




