package com.fis.ra;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SlidingMenuAdapter extends BaseAdapter {
	
	private Context context;
    private ArrayList<SlidingMenuItem> items;
    
    

	public SlidingMenuAdapter(Context context, ArrayList<SlidingMenuItem> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int index) {
		return items.get(index);
	}

	@Override
	public long getItemId(int index) {
		return index;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {	
		if (view == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.lv_item_sliding_menu, null);
        }
          
        ImageView imgIcon = (ImageView) view.findViewById(R.id.img_sliding_menu_item);
        TextView txtTitle = (TextView) view.findViewById(R.id.tv_sliding_menu_item);
        
        SlidingMenuItem item = items.get(index);
          
        imgIcon.setImageResource(item.getIcon());        
        txtTitle.setText(item.getTitle());
         
        return view;
	}

}

