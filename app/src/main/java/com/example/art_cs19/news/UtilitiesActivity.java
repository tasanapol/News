package com.example.art_cs19.news;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
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
import com.skyfishjy.library.RippleBackground;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.locks.Condition;

import de.hdodenhof.circleimageview.CircleImageView;

public class UtilitiesActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private String time, date, MonthName, DayName, YearName, DayOfWeekName, initialDayofWeekName;
    private int batLevel, DayOfWeek;
    private TextView showTime, showDate, showBattery, showWeather, showCondition, showLocation;
    private GoogleApiClient mGoogleApiClient;
    private TextToSpeech tts;
    private final int REQUEST_SPEECH = 100;
    private Weather weather;
    private float Temp;
    private PlaceLikelihood b;
    private String location;
    private Toolbar toolbar;


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
                        Temp = (weather.getTemperature(Weather.CELSIUS));
                        //showWeather.setText("Weather : " + weather.getTemperature(Weather.CELSIUS));
                        showWeather.setText(String.format(" %.0f", Temp) + " °C");
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
                            for (int i = 0; i < 1 && i < placeLikelihoodList.size(); i++) {
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

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        //กด
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    promtspeech();


                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {

                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }


    public void promtspeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "เทสๆ");
        startActivityForResult(intent, REQUEST_SPEECH);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SPEECH) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (matches.size() == 0) {


                } else {
                    //คำสั่งเสียง
                    String mostLikelyThingHeard = matches.get(0);

                    if (mostLikelyThingHeard.toUpperCase().equals("เวลา")) {
                        tts.speak("วัน " + DayOfWeekName + "  ที่" + DayName + "  เดือน " + MonthName + "ปี" + YearName + "ขณะนี้เวลา " + time + "นาฬิกา", TextToSpeech.QUEUE_FLUSH, null, "");

                    } else if (mostLikelyThingHeard.toUpperCase().equals("อุณหภูมิ")) {
                        tts.speak("ขณะนี้อุณหภูมิ" + Temp + "องศาเซลเซียส", TextToSpeech.QUEUE_FLUSH, null, "");

                    } else if (mostLikelyThingHeard.toUpperCase().equals("แบตเตอรี่")) {
                        tts.speak("ปริมาณแบตเตอรี่คือ " + batLevel + "เปอร์เซ็นต์", TextToSpeech.QUEUE_FLUSH, null, "");


                    } else if (mostLikelyThingHeard.toUpperCase().equals("สถานที่")) {
                        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
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
                                            for (int i = 0; i < 1 && i < placeLikelihoodList.size(); i++) {
                                                PlaceLikelihood p = placeLikelihoodList.get(i);
                                                showLocation.setText(p.getPlace().getName().toString());
                                                tts.speak("คุณกำลังอยู่บริเวณ" + p.getPlace().getName().toString(), TextToSpeech.QUEUE_FLUSH, null, "");
                                            }
                                        } else {
                                            showLocation.setText("สถานที่ว่างเปล่า");
                                        }
                                    }
                                });
                    }
                }

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /////////////////////////////////////////////////////////////////////

    private void findview() {
        showDate = (TextView) findViewById(R.id.showDate);
        showTime = (TextView) findViewById(R.id.showTime);
        showBattery = (TextView) findViewById(R.id.showBattery);
        showWeather = (TextView) findViewById(R.id.showWeather);
        showCondition = (TextView) findViewById(R.id.showCondition);
        showLocation = (TextView) findViewById(R.id.showLocation);
        tts = new TextToSpeech(this, this, "com.google.android.tts");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    @Override
    public void onInit(int status) {

    }
    @Override
    protected void onPause() {
        super.onPause();
        tts.stop();
    }
}
