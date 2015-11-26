package com.example.pitectors.pitection;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rnice01 on 11/23/2015.
 */
public class EventLogAdapter extends BaseAdapter {
    ArrayList<Events> list;
    private Activity context1;

    public EventLogAdapter(Activity context, ArrayList<Events> items)
    {
        context1 = context;
        this.list = items;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null){
            convertView = LayoutInflater.from(context1).inflate(R.layout.device_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) convertView.findViewById(R.id.deviceName);
            /**When the List View is first created, create a row with the custom layout
             * instance and store it to later add texts and images
             */
            convertView.setTag(viewHolder);
        }
        else{
            /**
             * Once the instance of the row item's control it will use from
             * already created controls which are stored in convertView as a
             * ViewHolder Instance
             * */
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.text.setText(list.get(position).getEventDevice());


        return convertView;
    }

    public class ViewHolder{
        public TextView text;
    }
}
