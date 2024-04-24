package com.example.javakurssi.ui.dashboard;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.javakurssi.R;
import com.example.javakurssi.databinding.FragmentDashboardBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Locale;

public class DashboardFragment extends Fragment implements LocationListener {

    private String TAG = "Currentlocation";
    private FragmentDashboardBinding binding;
    private LocationManager locationManager;
    private Location lastLocation;
    private Button locationButton;

    private String currentLocation;

    private TextInputLayout longitudeInfo;
    private TextInputLayout latitudeInfo;
    private TextInputLayout addressInfo;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return root;
        }

        try{
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, this);
        lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);}
        catch (Exception e){
            Log.e("error", e.getMessage());
        }

        addressUpdate();


        locationButton = (Button) root.findViewById(R.id.locationbutton);

        longitudeInfo = (TextInputLayout) root.findViewById(R.id.longitude);
        longitudeInfo.getEditText().setText(Double.toString(lastLocation.getLongitude()));

        latitudeInfo = (TextInputLayout) root.findViewById(R.id.latitude);
        latitudeInfo.getEditText().setText(Double.toString(lastLocation.getLatitude()));

        addressInfo = (TextInputLayout) root.findViewById(R.id.address);
        addressInfo.getEditText().setText(currentLocation);


        locationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleOnClickEvents(v);
                Log.d("TAG", "User tapped the location button");
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void handleOnClickEvents(View v) {
        //if else kokeile?
        switch(v.getId()){

            case R.id.locationbutton:
                Log.d("TAG", "User tapped the location button");
                Uri gmmIntentUri = Uri.parse("geo:" + lastLocation.getLatitude() + ", " + lastLocation.getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getContext().getPackageManager()) != null){
                    startActivity(mapIntent);
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        locationManager.removeUpdates(this);
        binding = null;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        lastLocation = location;
        addressUpdate();

        Log.d("TAG", String.valueOf(location));
        Log.d("TAG", String.valueOf(location.getLongitude()));
        Log.d("TAG",String.valueOf(location.getLatitude()));
        Log.d("TAG", String.valueOf(currentLocation));

        if(lastLocation !=null){
        longitudeInfo.getEditText().setText(Double.toString(lastLocation.getLongitude()));
        latitudeInfo.getEditText().setText(Double.toString(lastLocation.getLatitude()));
        addressInfo.getEditText().setText(currentLocation);}
    }

    public void addressUpdate(){

        try{
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(
                    lastLocation.getLatitude(),
                    lastLocation.getLongitude(),
                    1);
            Address address = addresses.get(0);
            currentLocation = address.getAddressLine(0);
        }catch (Exception e){
            Log.e("error", e.getMessage());
        }
    }
}

/*
    private String getAddress(Location location){
        currentLocation = "Address not available";
        try{
            addressUpdate();
        } catch (Exception e){
            Log.e("getlocation", e.getMessage());
        }
        return currentLocation;
    }*/

