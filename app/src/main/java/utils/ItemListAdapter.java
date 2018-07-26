package utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.computer.basic.quiz.craftystudio.computerbasic.R;

/**
 * Created by Aisha on 12/24/2017.
 */

public class ItemListAdapter extends ArrayAdapter {

    ArrayList<String> mTopicList;
    Context mContext;

    int mResourceID;

    ClickListener clickListener;

    public ItemListAdapter(@NonNull Context context, int resource, ArrayList<String> items) {
        super(context, resource);

        this.mTopicList = items;
        this.mContext = context;
        this.mResourceID = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.custom_quiz_textview, null, false);
        }
        //getting the view
        //  View view = layoutInflater.inflate(mResourceID, null, false);


        TextView topicNameTextview = (TextView) convertView.findViewById(R.id.customquiz_textview);
        topicNameTextview.setText((String)mTopicList.get(position));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemCLickListener(view,position);
                //  Toast.makeText(mContext, "Item clicked " + mTopicList.get(position), Toast.LENGTH_SHORT).show();
            }
        });


        // Toast.makeText(mContext, "Topic set", Toast.LENGTH_SHORT).show();
        return convertView;
    }

    @Override
    public int getCount() {
        return mTopicList.size();

    }

    public void setOnItemCLickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
