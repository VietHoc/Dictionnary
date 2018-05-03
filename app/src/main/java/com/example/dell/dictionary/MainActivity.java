package com.example.dell.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.dictionary.controllers.WordController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MyAdapter.ItemCustomListener,TextToSpeech.OnInitListener{

    private MyAdapter myadapter;
    private ListView listView,listView_navigation;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private EditText ed_search;

    public TextToSpeech tts;
    private int result;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tts = new TextToSpeech(this, this);


        listView=(ListView) findViewById(R.id.listview);


        myadapter=new MyAdapter(WordController.INSTANCE.getWords(),this);
        listView.setAdapter(myadapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final Word word=(Word) adapterView.getItemAtPosition(position);
                    Intent intent = new Intent(MainActivity.this, Detail_activity.class);
                    intent.putExtra("word_id", word.getId());
                    startActivity(intent);
            }
        });



        toolbar=(Toolbar) findViewById(R.id.toolbar);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);

        ed_search=(EditText) findViewById(R.id.ed_search);

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                myadapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();

        String[] arr_navi={"English-Vietnamese","Favorite list","Exit"};
        listView_navigation=(ListView) findViewById(R.id.listview_navi);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arr_navi);
        listView_navigation.setAdapter(adapter);
        listView_navigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                        break;
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
                return super.onOptionsItemSelected(item);
    }



    @Override
    public void onInit(int status) {
        if (status!=TextToSpeech.ERROR){
            Log.i("TAG",String.valueOf(status));
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
    public void onSpeak(int position,String text) {
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }


}