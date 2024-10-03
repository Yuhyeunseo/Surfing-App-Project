package com.example.day0528_2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class google_map extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gMap;
    MapFragment mapFrag;
    GroundOverlayOptions placeMark;
    Button btnPrev, btnNext, homeBtn;
    List<String> lines=new ArrayList<>();
    int placeCount=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        setTitle("해수욕장");
        mapFrag=(MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
        btnPrev=findViewById(R.id.btnpre);
        btnNext=findViewById(R.id.btnnext);
        homeBtn = findViewById(R.id.homeBtn);
        readCSV();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String line=lines.get(placeCount++);
                if(placeCount>lines.size()-1)
                    placeCount=0;
                String[]tokens=line.split(",");
                double lat=Double.parseDouble(tokens[0]);
                double lon=Double.parseDouble(tokens[1]);
                String restname = tokens[2];

                LatLng point;
                point=new LatLng(lat, lon);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,13));
                placeMark=new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.gps)).
                        position(point,500f,500f);
                gMap.addGroundOverlay(placeMark);
                Toast.makeText(getApplicationContext(),restname,Toast.LENGTH_LONG).show();
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String line=lines.get(placeCount--);
                if(placeCount<0)
                    placeCount=lines.size()-1;
                String []tokens=line.split(",");
                double lat=Double.parseDouble(tokens[0]);
                double lon=Double.parseDouble(tokens[1]);
                String restName = tokens[2];

                LatLng point;
                point=new LatLng(lat, lon);
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,13));
                placeMark=new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.gps)).
                        position(point,500f,500f);
                gMap.addGroundOverlay(placeMark);
                Toast.makeText(getApplicationContext(),restName,Toast.LENGTH_LONG).show();
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        gMap=map;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.568256, 126.897240),13));
        gMap.getUiSettings().setZoomControlsEnabled(true);
    }

    public void readCSV()
    {
        InputStream inputStream = getResources().openRawResource(R.raw.sea);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try{
            String line;
            line=reader.readLine();
            while((line =reader.readLine()) != null)
            {
                lines.add(line);
            }
            reader.close();
        }catch (IOException e){}
    }
}