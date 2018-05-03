package com.example.dell.dictionary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 7/30/2016.
 */
public class MyAdapter extends BaseAdapter implements Filterable {

    private List<Word> listdata,listFavorite;
    private LayoutInflater layoutInflater;
    private  ItemCustomListener itemCustomListener;
    private ItemFavorite itemFavorite;
    private Context context;
    //private List<Word> words;
    private Word word;

    private List<Word> mOriginalValues;



    public MyAdapter(List<Word> listdata,List<Word> listFavorite, Context context) {
        layoutInflater=LayoutInflater.from(context);
        this.context = context;
        this.listdata=listdata;
        this.listFavorite=listFavorite;
        this.itemCustomListener=(ItemCustomListener)context;
    }

    @Override
    public int getCount() {

        return this.listdata.size();
    }

    @Override
    public Object getItem(int i) {
        return listdata.get(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        Word word=listdata.get(position);
        if (view == null) {
            view = layoutInflater.inflate(R.layout.row_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) view.findViewById(R.id.textview);
            holder.imbspeak=(ImageButton) view.findViewById(R.id.imbspeaker);
            holder.imbstar=(ImageButton) view.findViewById(R.id.imbstar);
//            if (checkWord(listFavorite,word)){
//                holder.imbstar.setImageResource(R.drawable.staron);
//            }
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }



        word = this.listdata.get(position);
        holder.textView.setText(word.getWord());
        holder.imbspeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("TAG",holder.textView.getText().toString());

                if (itemCustomListener!=null)
                    itemCustomListener.onSpeak(position,holder.textView.getText().toString());
            }
        });

//        holder.imbstar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (itemFavorite!=null)
//                    itemFavorite.onFavorite(position);
//            }
//        });
        return view;
    }


    static class ViewHolder {
        TextView textView;
        ImageButton imbspeak;
        ImageButton imbstar;
    }



    @Override
    public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    Filter.FilterResults results = new FilterResults();
                    ArrayList<Word> FilteredArrayList = new ArrayList<>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<Word>(listdata); // saves the original data in mOriginalValues
                    }
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < mOriginalValues.size(); i++) {
                            String data = mOriginalValues.get(i).getWord();
                            if (data.toLowerCase().startsWith(constraint.toString())) {
                                FilteredArrayList.add(new Word(mOriginalValues.get(i).getWord(),mOriginalValues.get(i).getDetail(),mOriginalValues.get(i).getId()));
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrayList.size();
                        results.values = FilteredArrayList;
                    }
                    return results;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    listdata = (List<Word>) results.values;
                    notifyDataSetChanged();
                }
            };
            return filter;
        }
    public interface  ItemCustomListener{
         void onSpeak(int position,String text);
    }

    public interface ItemFavorite{
        void onFavorite(int position);
    }
//    public boolean checkWord(List<Word> list,Word word){
//        boolean bl=false;
//        for (Word w :
//                list) {
//            if (w.getId() == word.getId()){
//                bl=true;
//                break;
//            }
//        }
//        return bl;
//    }
}
