package com.fis.ra;


import java.io.InputStream;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ImagesFragment extends Fragment {
	
	private static InputStream stringImagePath = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_images,
				container, false);
		
		ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
		
		ImageAdapter adapter = new ImageAdapter(getActivity().getApplicationContext(),stringImagePath);
		viewPager.setAdapter(adapter);

		return rootView;
	}
	
	public static void setmyText(final String string) {
		Log.d("Prueba de mensajes", string);
		}

	public static void setImagePath(final InputStream path) {
		stringImagePath = path;
	}
}
