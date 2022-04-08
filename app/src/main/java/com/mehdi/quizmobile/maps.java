package com.mehdi.quizmobile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class maps extends AppCompatActivity implements OnMapReadyCallback {
    FusedLocationProviderClient client;

    SupportMapFragment smf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        smf = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        smf.getMapAsync(this);
        client = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(maps.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(maps.this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 44
            );
        }
    }
    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(maps.this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, 44
            );

        }else {


            Task<Location> task = client.getLastLocation();
            task.addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        smf.getMapAsync(new OnMapReadyCallback() {
                            @Override
                            public void onMapReady(@NonNull GoogleMap googleMap) {
                                LatLng l = new LatLng(location.getLatitude(), location.getLongitude());
                                MarkerOptions options = new MarkerOptions().position(l).title("i am here");
                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(l, 10));
                                googleMap.addMarker(options);
                            }
                        });

                    }
                }
            });

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}