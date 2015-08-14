package com.codepath.uncooperativelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyAdapter extends ArrayAdapter<String> {
    static final int MIN_STATUS = 10000;
    static final int MAX_WORD_LENGTH = 8;
    static final int MIN_CAPTION_LENGTH = 10;
    static final int MIN_WORD_LENGTH = 2;
    static final char FILLER_CHARACTER = 'X';
    static final char SPACE_CHARACTER = ' ';

    public MyAdapter(Context context, List<String> myList) {
        super(context, 0, myList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_my_item, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }

        String item = getItem(position);

        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.mStatusTextView.setText(String.valueOf(generateRandomInt(MIN_STATUS)));
        holder.mCaptionTextView.setText(item);

        return convertView;
    }

    private static class ViewHolder {
        public TextView mCaptionTextView;
        public TextView mStatusTextView;

        public ViewHolder(View itemView) {
            mCaptionTextView = (TextView) itemView.findViewById(R.id.captionTextView);
            mStatusTextView = (TextView) itemView.findViewById(R.id.statusTextView);
        }
    }

    static int generateRandomInt(int minimum) {
        return minimum + new Random().nextInt(minimum * 9);
    }

    public static List<String> generateList(int size) {
        List<String> list = new ArrayList<>();

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < size; i++) {
            builder.setLength(0);

            int captionLength = generateRandomInt(MIN_CAPTION_LENGTH);
            int wordLength = MIN_WORD_LENGTH + new Random().nextInt(MAX_WORD_LENGTH);

            for(int j = 0; j < captionLength; j++) {
                if(j % wordLength == 0) {
                    builder.append(SPACE_CHARACTER);
                }
                else {
                    builder.append(FILLER_CHARACTER);
                }
            }
            list.add(builder.toString());
        }
        return list;
    }
}
