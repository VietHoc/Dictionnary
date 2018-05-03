package com.example.dell.dictionary;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.dictionary.controllers.FavoriteController;
import com.example.dell.dictionary.controllers.WordController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by DELL on 8/4/2016.
 */
public class Detail_activity extends AppCompatActivity implements MyPagerAdapter.TTS,TextToSpeech.OnInitListener{

    private Word word;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_viewpager);

        tts=new TextToSpeech(this,this);

        Bundle bundle = getIntent().getExtras();
        word = WordController.INSTANCE.getById(bundle.getInt("word_id"));

        init();

    }


    private void init() {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(WordController.INSTANCE.getWords(), this);
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(pagerAdapter);
        pager.setCurrentItem(WordController.INSTANCE.getWords().indexOf(word));
    }

    @Override
    public void onInit(int status) {
        if (status!=TextToSpeech.ERROR){
            tts.setLanguage(Locale.US);
        }else {
            Toast.makeText(getApplicationContext(),"Feature not sopportedin your device",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        if (tts!=null){
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if (tts!=null){
            tts.stop();
        }
        super.onPause();
    }


    @Override
    public void speackOut(int position, String str) {
        tts.speak(str,TextToSpeech.QUEUE_FLUSH,null);
    }
}
