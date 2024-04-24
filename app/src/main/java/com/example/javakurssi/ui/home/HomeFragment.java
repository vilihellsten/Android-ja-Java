package com.example.javakurssi.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.javakurssi.DataActivity;
import com.example.javakurssi.GameActivity;
import com.example.javakurssi.R;
import com.example.javakurssi.Valuuttamuunnin;
import com.example.javakurssi.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private static final String TAG = "MainActivity2";
    private Button helloButton;
    private Button startButton;
    private TextView helloView;

    private Button muunnin;
    private EditText search;

    private Button searchButton;
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

        searchButton = (Button) root.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
            }
        });

        search = (EditText) root.findViewById(R.id.search);

        muunnin = (Button) root.findViewById(R.id.muunnin);

        muunnin.setOnClickListener(new View.OnClickListener() {
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
                Log.i(TAG, "User tapped the Hello button");
                if(helloView.getVisibility() == View.GONE )
                {
                    helloView.setVisibility(View.VISIBLE);
                } else {
                    helloView.setVisibility(View.GONE);
                }
                break;
            case R.id.startButton:
                Log.i(TAG, "User tapped the Start Game button");
                startActivity(new Intent(getActivity(), GameActivity.class));
                break;
            case R.id.searchButton:
                Log.i(TAG, "User tapped the search button");
                Intent i = new Intent(getActivity(), DataActivity.class);
                String value = search.getText().toString();
                Log.e("search",value);
                i.putExtra("search", value);
                startActivity(i);
                break;
            case R.id.muunnin:
                Log.i(TAG, "User tapped the Start Game button");
                startActivity(new Intent(getActivity(), Valuuttamuunnin.class));
                break;
        }
    }


}