package com.fis.ra;


import android.os.Bundle;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ImagesFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_images,
				container, false);
		
		ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
		ImageAdapter adapter = new ImageAdapter(getActivity().getApplicationContext());
		viewPager.setAdapter(adapter);

		return rootView;
	}
	
	public static void setmyText(final String string) {
		Log.d("Prueba de mensajes", string);
		}
}
