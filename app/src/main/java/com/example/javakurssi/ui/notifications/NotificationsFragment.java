package com.example.javakurssi.ui.notifications;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.javakurssi.R;
import com.example.javakurssi.databinding.FragmentNotificationsBinding;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private NumberPicker choose;

    private int timer; //pitää numberpicker valuen paikallaan

    private int timer2; //ottaa alkp. valuen timeristä ja käyttää ja muokkaa tätä ajastimessa

    private Ringtone defRingtone;
    private CountDownTimer countDownTimer;
    private TextView counter;
    private MaterialButtonToggleGroup materialButtonToggleGroup;

    private String[] choosevals;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        defRingtone = RingtoneManager.getRingtone(getActivity(), Settings.System.DEFAULT_RINGTONE_URI);

        materialButtonToggleGroup = root.findViewById(R.id.toggle);
        counter = root.findViewById(R.id.counter);
        choose = root.findViewById(R.id.choose);

        choose.setMinValue(0);
        choose.setMaxValue(60);

        choosevals = new String[61];
        for (int i = 0; i < 61; i++) {
            choosevals[i] = String.valueOf(i);
        }
        choose.setDisplayedValues(choosevals);


        choose.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                // Code here executes on main thread after user selects value
                Log.e("num1", String.valueOf(i));
                Log.e("num1", String.valueOf(i1));
                timer = i1;
                Log.e("num2", String.valueOf(timer));
                timer2 = timer;

            }
        });

        materialButtonToggleGroup.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.start) {
                        Log.e("timer", "start");
                        startTimer();
                    } else if (checkedId == R.id.pause) {
                        Log.e("timer", "pause");
                        pauseTimer();
                    } else if (checkedId == R.id.stop) {
                        Log.e("timer", "stop");
                        stopTimer();
                    }
                }
            }
        });

        return root;

    }

    public void startTimer(){
        defRingtone.stop();
        if (countDownTimer != null){ //tämä turhaa atm kuului aikaisempaan ratkaisuun, tarkista vielä
            countDownTimer.cancel();
        }
        if (timer2 == 0){
            timer2 = timer;
        }
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.buttonanimation);
        counter.clearAnimation();
        countDownTimer = new CountDownTimer(timer2 * 1000 + 1000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer2 = (int) millisUntilFinished / 1000;
                counter.setText(String.valueOf(millisUntilFinished / 1000));
            }

            public void onFinish() {
                counter.setText("TIME!");
                counter.startAnimation(animation);
                defRingtone.play();
            }
        }.start();
    }

    public void pauseTimer(){
        defRingtone.stop();
        if(timer2 == 0)
            counter.setText("");
        try {
            countDownTimer.cancel();
            counter.clearAnimation();
        }
        catch (Exception e){
        }
    }

    public void stopTimer(){
        countDownTimer.cancel();
        counter.setText("");
        timer2 = timer;
        counter.clearAnimation();
        defRingtone.stop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        defRingtone.stop();
        binding = null;
    }


}