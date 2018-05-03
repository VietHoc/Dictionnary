package com.example.dell.dictionary;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by DELL on 8/4/2016.
 */
public class Detail_activity extends AppCompatActivity implements MyPagerAdapter.TTS,TextToSpeech.OnInitListener{

    private MyPagerAdapter pagerAdapter;
    private MyDatabase myDatabase;
    private ViewPager pager;
    private ImageButton imbstar,imbspeak;
    private TextView tvWord;
    private MyDatabaseLove databaseLove;
    List<Word> words;
    private Word word;

    private TextToSpeech tts;
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_viewpager);

        tts=new TextToSpeech(this,this);


        tvWord = (TextView) findViewById(R.id.tvWord);
        imbspeak=(ImageButton) findViewById(R.id.imbspeak);
        imbstar=(ImageButton) findViewById(R.id.imbstar);

        Bundle bundle = getIntent().getExtras();
        word = (Word) bundle.get("word");

        Toast.makeText(this, word.getWord() + word.getId(), Toast.LENGTH_SHORT).show();


        try {
            myDatabase = new MyDatabase(this);
            databaseLove = new MyDatabaseLove(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        databaseLove.open();
        init();
        if (databaseLove.getList() != null) {
            for (Word i : databaseLove.getList()) {
                if (i.getWord().equals(tvWord.getText().toString())) {
                    imbstar.setImageDrawable(getResources().getDrawable(R.drawable.staron));
                }
            }
        }
        pager.setCurrentItem(positionWord(word));
        databaseLove.close();

    }


    private void init() {
        words = new ArrayList<>();
        myDatabase.opendatabase();
        words = (ArrayList<Word>) myDatabase.getList();
        myDatabase.close();
        pagerAdapter = new MyPagerAdapter(words, this);
        pager = (ViewPager) findViewById(R.id.viewPager);
        pager.setAdapter(pagerAdapter);
    }

    private int positionWord(Word word) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).getId() == word.getId())
                return i;
        }
        return 0;
    }

    @Override
    public void onInit(int status) {
        if (status!=TextToSpeech.ERROR){
            result=tts.setLanguage(Locale.US);
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
