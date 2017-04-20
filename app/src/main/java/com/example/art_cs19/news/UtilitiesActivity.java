package com.example.art_cs19.news;

import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.awareness.Awareness;
import com.google.android.gms.awareness.snapshot.PlacesResult;
import com.google.android.gms.awareness.snapshot.WeatherResult;
import com.google.android.gms.awareness.state.Weather;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceLikelihood;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.locks.Condition;

import de.hdodenhof.circleimageview.CircleImageView;

public class UtilitiesActivity extends AppCompatActivity {
    private String time, date, MonthName, DayName, YearName, DayOfWeekName, initialDayofWeekName;
    private int batLevel, DayOfWeek;
    private TextView showTime, showDate, showBattery, showWeather, showCondition, showLocation;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utilities);
        de.hdodenhof.circleimageview.CircleImageView image = (CircleImageView) findViewById(R.id.image_circle);
        image.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        DateCalender();
        findview();
        initSnapshots();

        showTime.setText(time);
        showDate.setText(DayOfWeekName + "\n " + DayName + " " + MonthName + " " + YearName);
        showBattery.setText("แบตเตอรี่ " + batLevel + " %");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        } else {

        }
    }

    private void initSnapshots() {
        //runtime permission ขอเข้าถึง location
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 12345
            );
        }
        Awareness.SnapshotApi.getWeather(mGoogleApiClient)
                .setResultCallback(new ResultCallback<WeatherResult>() {
                    @Override
                    public void onResult(@NonNull WeatherResult weatherResult) {
                        if (!weatherResult.getStatus().isSuccess()) {
                            Toast.makeText(getApplicationContext(), "Weather: หาไม่เจอ", Toast.LENGTH_LONG).show();
                            showWeather.setText("Weather : หาไม่เจอ");
                            return;
                        }
                        Weather weather = weatherResult.getWeather();
                        //showWeather.setText("Weather : " + weather.getTemperature(Weather.CELSIUS));
                        showWeather.setText(String.format(" %.0f", weather.getTemperature(Weather.CELSIUS))+" °C");
                        showCondition.setText("" + weather.getConditions());
                    }
                });
        Awareness.SnapshotApi.getPlaces(mGoogleApiClient)
                .setResultCallback(new ResultCallback<PlacesResult>() {
                    @Override
                    public void onResult(@NonNull PlacesResult placesResult) {
                        if (!placesResult.getStatus().isSuccess()) {
                            //Log.e(TAG, "Could not get places.");
                            showLocation.setText("หาที่ไม่เจอ");
                            return;
                        }
                        List<PlaceLikelihood> placeLikelihoodList = placesResult.getPlaceLikelihoods();
                        // Show the top 5 possible location results.
                        if (placeLikelihoodList != null) {
                            for (int i = 0; i < 5 && i < placeLikelihoodList.size(); i++) {
                                PlaceLikelihood p = placeLikelihoodList.get(i);
                                showLocation.setText(p.getPlace().getName().toString());
                                //Log.i(TAG, p.getPlace().getName().toString() + ", likelihood: " + p.getLikelihood());
                            }
                        } else {
                           // Log.e(TAG, "Place is null.");
                            showLocation.setText("สถานที่ว่างเปล่า");
                        }
                    }
                });
    }

    private void findview() {
        showDate =(TextView)findViewById(R.id.showDate);
        showTime = (TextView)findViewById(R.id.showTime);
        showBattery = (TextView)findViewById(R.id.showBattery);
        showWeather = (TextView)findViewById(R.id.showWeather);
        showCondition = (TextView)findViewById(R.id.showCondition);
        showLocation = (TextView)findViewById(R.id.showLocation);
        //awareness
        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Awareness.API).build();
        mGoogleApiClient.connect();

    }


    public void DateCalender() {
        //แบตเตอรี่
        BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
        batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        //เวลา
        DateFormat df = new SimpleDateFormat("HH:mm"); //format time
        time = df.format(Calendar.getInstance().getTime());
        //วันที่
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat DayDate = new SimpleDateFormat("dd");
        DayName = DayDate.format(cal.getTime());
        SimpleDateFormat MonthDate = new SimpleDateFormat("MMMM");
        MonthName = MonthDate.format(cal.getTime());
        SimpleDateFormat YearDate = new SimpleDateFormat("yyyy");
        YearName = YearDate.format(cal.getTime());
        DayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        DayOfWeekName = String.valueOf(DayOfWeek);
        initialDayofWeekName = String.valueOf(DayOfWeek);

        switch (DayOfWeek) {
            case 1:
                DayOfWeekName = "อาทิตย์";
                initialDayofWeekName = "อา.";
                break;
            case 2:
                DayOfWeekName = "จันทร์";
                initialDayofWeekName = "จ.";
                break;
            case 3:
                DayOfWeekName = "อังคาร";
                initialDayofWeekName = "อ.";
                break;
            case 4:
                DayOfWeekName = "พุธ";
                initialDayofWeekName = "พ.";
                break;
            case 5:
                DayOfWeekName = "พฤหัสบดี";
                initialDayofWeekName = "พฤ.";
                break;
            case 6:
                DayOfWeekName = "ศุกร์";
                initialDayofWeekName = "ศ.";
                break;
            case 7:
                DayOfWeekName = "เสาร์";
                initialDayofWeekName = "ส.";
                break;
        }
    }
}
