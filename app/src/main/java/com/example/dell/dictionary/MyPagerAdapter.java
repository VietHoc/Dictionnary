package com.example.dell.dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        TextView tvDetail=(TextView) view.findViewById(R.id.tvDetail);
        ImageButton imbspeak=(ImageButton) view.findViewById(R.id.imbspeak);

        tvWord.setText(words.get(position).getWord());

        tvDetail.setText(words.get(position).getDetail().substring(words.get(position).getWord().length()));

        imbspeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speackOut(position,tvWord.getText().toString());
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
