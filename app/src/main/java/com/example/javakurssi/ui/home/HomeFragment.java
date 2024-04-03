package com.example.javakurssi.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.javakurssi.GameActivity;
import com.example.javakurssi.R;
import com.example.javakurssi.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private static final String TAG = "MainActivity2";
    private Button helloButton;
    private Button startButton;
    private TextView helloView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textHome;
        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        helloView = (TextView) root.findViewById(R.id.helloView);
        helloButton = (Button) root.findViewById(R.id.helloButton);

        helloButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        startButton = (Button) root.findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void handleOnClickEvents(View v) {
        //if else kokeile?
        switch(v.getId()){
            case R.id.helloButton:
                Log.d(TAG, "User tapped the Hello button");
                if(helloView.getVisibility() == View.GONE )
                { //pitää ehkä korjata... ei ole constraintteja helloViewvissä joten muut elementit eivät liiku, gone toimii
                    helloView.setVisibility(View.VISIBLE);
                } else {
                    helloView.setVisibility(View.GONE);
                }
                break;
            case R.id.startButton:
                Log.d(TAG, "User tapped the Start Game button");
                startActivity(new Intent(getActivity(), GameActivity.class));
                break;
        }
    }


}