package com.example.art_cs19.news;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Call extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private ImageButton btn1;
    private TextToSpeech tts;
    private static final int REQUEST_SPEECH = 0;
    private ViewStub stubGrid;
    private ViewStub stubList;
    private ListView listView;
    private GridView gridView;
    private ListViewAdapter listViewAdapter;
    private GridViewAdpater gridViewAdpater;
    private List<ContactAdapter> productList;
    private int currentViewMode = 0;
    static final int VIEW_MODE_LISTVIEW = 0;
    static final int VIEW_MODE_GRIDVIEW = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        btn1 = (ImageButton) findViewById(R.id.btn1);
        tts = new TextToSpeech(this, this, "com.google.android.tts");

        stubList = (ViewStub) findViewById(R.id.stub_list);
        stubGrid = (ViewStub) findViewById(R.id.stub_grid);
        stubList.inflate();
        stubGrid.inflate();
        listView = (ListView) findViewById(R.id.mylistview);
        gridView = (GridView) findViewById(R.id.mygridview);

        getProductList();

        switchView();

        SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
        currentViewMode = sharedPreferences.getInt("currentViewMode", VIEW_MODE_LISTVIEW);
        listView.setOnItemClickListener(onItemClickListener);
        gridView.setOnItemClickListener(onItemClickListener);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 10);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


    }


    private void switchView() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            stubList.setVisibility(View.VISIBLE);
            stubGrid.setVisibility(View.GONE);
        } else {
            stubList.setVisibility(View.GONE);
            stubGrid.setVisibility(View.VISIBLE);
        }

        setAdapters();

    }

    private void setAdapters() {
        if (VIEW_MODE_LISTVIEW == currentViewMode) {
            listViewAdapter = new ListViewAdapter(this, R.layout.list_item, productList);
            listView.setAdapter(listViewAdapter);
        } else {
            gridViewAdpater = new GridViewAdpater(this, R.layout.grid_item, productList);
            gridView.setAdapter(gridViewAdpater);
        }
    }

    public List<ContactAdapter> getProductList() {
        productList = new ArrayList<>();
        productList.add(new ContactAdapter(R.drawable.pic13, "รวมแท๊กซี่ไทย", "Tel : 02-8836621"));
        productList.add(new ContactAdapter(R.drawable.pic1, "แจ้งเหตุด่วนเหตุร้าย", "Tel : 191"));
        productList.add(new ContactAdapter(R.drawable.pic2, "แจ้งเหตุไฟไหม้", "Tel : 199"));
        productList.add(new ContactAdapter(R.drawable.pic3, "สายด่วนตำรวจท่องเที่ยว", "Tel : 1155"));
        productList.add(new ContactAdapter(R.drawable.pic4, "หน่วยกู้ชีพ วชิรพยาบาล", "Tel : 1554"));
        productList.add(new ContactAdapter(R.drawable.pic5, "ศูนย์จราจร", "Tel : 1197"));
        productList.add(new ContactAdapter(R.drawable.pic6, "เหตุด่วนทางน้ำ", "Tel : 1199"));
        productList.add(new ContactAdapter(R.drawable.pic7, "รับแจ้งรถหาย/ถูกขโมย", "Tel : 1192"));
        productList.add(new ContactAdapter(R.drawable.pic8, "หน่วยแพทย์ฉุกเฉิน(ทั่วไทย)", "Tel : 1669"));
        productList.add(new ContactAdapter(R.drawable.pic9, "ศูนย์เตือนภัยพิบัติแห่งชาติ", "Tel : 192"));
        productList.add(new ContactAdapter(R.drawable.pic10, "สถานีวิทยุร่วมด้วยช่วยกัน", "Tel : 1677"));
        productList.add(new ContactAdapter(R.drawable.pic11, "แจ้งเหตุไฟฟ้าขัดข้อง", "Tel : 1130"));
        productList.add(new ContactAdapter(R.drawable.pic12, "แจ้งเหตุประปาขัดข้อง", "Tel : 1125"));
        return productList;
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(getApplicationContext(), "ddd", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.call_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_1:
                if (VIEW_MODE_LISTVIEW == currentViewMode) {
                    currentViewMode = VIEW_MODE_GRIDVIEW;
                } else {
                    currentViewMode = VIEW_MODE_LISTVIEW;
                }

                switchView();
                SharedPreferences sharedPreferences = getSharedPreferences("ViewMode", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.commit();
                break;
        }
        return true;
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
                    promtspeech();
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
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "เลือกหัวข้อ");
        startActivityForResult(intent, REQUEST_SPEECH);

    }


    public void onInit(int i) {
        if (i == TextToSpeech.SUCCESS) {
            tts.setLanguage(new Locale("th"));

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            if (matches.size() == 0) {
                tts.speak("กรุณาเลือกคำสั่งเสียง", TextToSpeech.QUEUE_FLUSH, null, "");
            } else {
                //คำสั่งเสียง
                String mostLikelyThingHeard = matches.get(0);

                if (mostLikelyThingHeard.toUpperCase().equals("แทร๊คซี่")) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:028836621"));
                    try {
                        startActivity(intent);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                    tts.speak("โทร", TextToSpeech.QUEUE_FLUSH, null, "");

                } else if (mostLikelyThingHeard.toUpperCase().equals("เหตุด่วนเหตุร้าย")) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:191"));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(intent);

                    tts.speak("โทร", TextToSpeech.QUEUE_FLUSH, null, "");

                }else if(mostLikelyThingHeard.toUpperCase().equals("เหตุไฟไหม้")){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:199"));
                    try{
                        startActivity(intent);
                    }catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                    tts.speak("โทร", TextToSpeech.QUEUE_FLUSH, null, "");

                }else if(mostLikelyThingHeard.toUpperCase().equals("สายด่วนตำรวจท่องเที่ยว")){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:1155"));
                    try{
                        startActivity(intent);
                    }catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                    tts.speak("โทร", TextToSpeech.QUEUE_FLUSH, null, "");

                }else if(mostLikelyThingHeard.toUpperCase().equals("หน่วยกู้ชีพ")){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:1554"));
                    try{
                        startActivity(intent);
                    }catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                    tts.speak("โทร", TextToSpeech.QUEUE_FLUSH, null, "");

                }else if(mostLikelyThingHeard.toUpperCase().equals("ศูนย์จราจร")){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:1197"));
                    try{
                        startActivity(intent);
                    }catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                    tts.speak("โทร", TextToSpeech.QUEUE_FLUSH, null, "");

                }else if(mostLikelyThingHeard.toUpperCase().equals("เหตุด่วนทางน้ำ")){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:1199"));
                    try{
                        startActivity(intent);
                    }catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                    tts.speak("โทร", TextToSpeech.QUEUE_FLUSH, null, "");

                }else if(mostLikelyThingHeard.toUpperCase().equals("แจ้งรถหาย")){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:1192"));
                    try{
                        startActivity(intent);
                    }catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                    tts.speak("โทร", TextToSpeech.QUEUE_FLUSH, null, "");

                }else if(mostLikelyThingHeard.toUpperCase().equals("หน่วยแพทน์ฉุกเฉิน")){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:1669"));
                    try{
                        startActivity(intent);
                    }catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                    tts.speak("โทร", TextToSpeech.QUEUE_FLUSH, null, "");

                }else if(mostLikelyThingHeard.toUpperCase().equals("ศูนย์เตือนภัยพิบัติ")){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:192"));
                    try{
                        startActivity(intent);
                    }catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                    tts.speak("โทร", TextToSpeech.QUEUE_FLUSH, null, "");

                }else if(mostLikelyThingHeard.toUpperCase().equals("ร่วมด้วยช่วยกัน")){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:1677"));
                    try{
                        startActivity(intent);
                    }catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                    tts.speak("โทร", TextToSpeech.QUEUE_FLUSH, null, "");

                }else if(mostLikelyThingHeard.toUpperCase().equals("ไฟฟ้าขัดข้อง")){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:1130"));
                    try{
                        startActivity(intent);
                    }catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                    tts.speak("โทร", TextToSpeech.QUEUE_FLUSH, null, "");

                }else if(mostLikelyThingHeard.toUpperCase().equals("ประปาขัดข้อง")){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:1125"));
                    try{
                        startActivity(intent);
                    }catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "yourActivity is not founded", Toast.LENGTH_SHORT).show();
                    }
                    tts.speak("โทร", TextToSpeech.QUEUE_FLUSH, null, "");
                }
            }
        }
    }
}
