package com.example.dell.dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dell.dictionary.controllers.FavoriteController;

import java.util.List;

/**
 * Created by DELL on 8/4/2016.
 */
public class MyPagerAdapter extends android.support.v4.view.PagerAdapter {

    List<Word> words;
    Context context;
    TTS tts;

    public MyPagerAdapter(List<Word> words, Context context) {
        this.words = words;
        this.context = context;
        tts=(TTS) context;
    }

    @Override
    public float getPageWidth(int position) {
        return 1f;
    }

    @Override
    public int getCount() {
        return words.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LayoutInflater layoutInflater =LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.viewpager,container,false);

        final TextView tvWord=(TextView) view.findViewById(R.id.tvWord);
        final TextView tvDetail=(TextView) view.findViewById(R.id.tvDetail);
        final ImageButton imbspeak=(ImageButton) view.findViewById(R.id.imbspeak);
        final ImageButton imbstar = (ImageButton)view.findViewById(R.id.imbstar);

        final Word word = words.get(position);

        tvWord.setText(word.getWord());
        imbstar.setActivated(FavoriteController.INSTANCE.contains(word));
        tvDetail.setText(word.getDetail().substring(word.getWord().length()));

        imbspeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speackOut(position,tvWord.getText().toString());
            }
        });

        imbstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteController.INSTANCE.toggleAdd(word);
                imbstar.setActivated(FavoriteController.INSTANCE.contains(word));
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
    public interface TTS{
        void speackOut(int position,String str);
    }
}
