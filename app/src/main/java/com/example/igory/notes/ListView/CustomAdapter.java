package com.example.igory.notes.ListView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.igory.notes.R;

import java.util.List;

/**
 * Created by igory on 18.03.2018.
 */

public class CustomAdapter extends BaseAdapter {

    private List<ListItem> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomAdapter(Context context, List<ListItem> listData)
    {
        this.context = context;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= convertView;
        if(view==null){
//получаем LayoutInflater для работы с layout-ресурсами
            view= layoutInflater.inflate(R.layout.list_item, parent,false);
        }

        ListItem p=((ListItem) getItem(position));

// заполняем View в пункте списка данными
        ((TextView) view.findViewById(R.id.textView3)).setText(p.getHead());
        ((TextView) view.findViewById(R.id.textView4)).setText(p.getDescription());
        ((TextView) view.findViewById(R.id.textView5)).setText(p.getDate());
        ((ImageView) view.findViewById(R.id.imageView3)).setBackgroundColor(p.getColor());

        return view;
    }

}
