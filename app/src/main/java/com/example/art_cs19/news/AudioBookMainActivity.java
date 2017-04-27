package com.example.art_cs19.news;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.neokree.materialtabs.MaterialTabHost;

public class AudioBookMainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabaseUser;
    private int REQUEST_SPEECH = 1;
    private TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_book_main);

        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("User");
        mDatabaseUser.keepSynced(true);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(AudioBookMainActivity.this, LogInActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AudioBookMainActivity.this, PostAudioBookActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        };

        ////toolBar///
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tts = new TextToSpeech(this, this, "com.google.android.tts");


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "บันเทิง");
        adapter.addFragment(new TwoFragment(), "สารคดี");
        adapter.addFragment(new ThreeFragment(), "ความรู้");
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            mAuth.addAuthStateListener(mAuthListener);
        }
        if (item.getItemId() == R.id.action_logout) {
            mAuth.signOut();
            Intent intent = new Intent(AudioBookMainActivity.this, LogInActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();

    }
/////////////speech prompt Zone /////////////////////////////////

    public void promtspeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "เลือกหัวข้อ");
        startActivityForResult(intent, REQUEST_SPEECH);
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
                    tts.speak("หนังสือเล่มที่1 คือ " + OneFragment.SoundList1
                                    + "หนังสือเล่มที่2 คือ" + OneFragment.SoundList2
                                    + "หนังสือเล่มที่3 คือ" + OneFragment.SoundList3
                                    + "หนังสือเล่มที่4 คือ" + OneFragment.SoundList4

                            , TextToSpeech.QUEUE_FLUSH, null, "");

                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SPEECH) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                if (matches.size() == 0) {
                    try {
                        promtspeech();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    //คำสั่งเสียง
                    final String mostLikelyThingHeard = matches.get(0);
                    Intent singleintent = new Intent(AudioBookMainActivity.this, SingleAudioBookActivity.class);
                    if (mostLikelyThingHeard.toUpperCase().equals("1")) {
                        singleintent.putExtra("post_key", OneFragment.PageList1);
                        startActivity(singleintent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("2")) {
                        singleintent.putExtra("post_key", OneFragment.PageList2);
                        startActivity(singleintent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("3")) {
                        singleintent.putExtra("post_key", OneFragment.PageList3);
                        startActivity(singleintent);
                    } else if (mostLikelyThingHeard.toUpperCase().equals("4")) {
                        singleintent.putExtra("post_key", OneFragment.PageList4);
                        startActivity(singleintent);
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /////////////speech prompt Zone /////////////////////////////////
    private void speakWords(String speech) {
        if (tts != null) {
            tts.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            tts.setLanguage(new Locale("th"));
            tts.setSpeechRate((float) 1);
            speakWords("ขณะนี้คุณกำลังอยู่ในหน้าหนังสือเสียง กรุณาเลือกหนังสือเสียง หรือกดปุ่มลดเสียงเพื่อฟังรายการหนังสือ");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts != null)
        {
            tts.stop();
            tts.shutdown();
        }
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        tts.stop();
        tts.shutdown();
    }
}
