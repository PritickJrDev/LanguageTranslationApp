package com.e.bodobhasha;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {

    int mColorResourceId;

    public WordAdapter(Context context, ArrayList<Word> words, int mColorResourceId) {
        super(context,0, words);
        this.mColorResourceId = mColorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // super.getView(position, convertView, parent);
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        final Word currentWord = getItem(position);
        TextView bodoTextView = (TextView) listItemView.findViewById(R.id.bodo_text_view);
        bodoTextView.setText(currentWord.getMBodoTranslation());

        TextView englishTextView = (TextView) listItemView.findViewById(R.id.english_text_view);
        englishTextView.setText(currentWord.getMDefaultTranslation());

        //set image for number,color,family activity and disable for phrases activity
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);
        if(currentWord.hasImage()) {
            imageView.setImageResource(currentWord.getMImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }
        else{
            imageView.setVisibility(View.GONE);
        }

        //change color on different activities
        View bgColor = listItemView.findViewById(R.id.bg_color);
        Drawable drawable = ContextCompat.getDrawable(getContext(), mColorResourceId);
        bgColor.setBackground(drawable);
//        int color = ContextCompat.getColor(getContext(), mColorResourceId);
//        bgColor.setBackgroundColor(color);


        return listItemView;
    }
}
