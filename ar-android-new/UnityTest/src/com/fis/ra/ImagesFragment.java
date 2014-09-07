package com.fis.ra;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ImagesFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_images,
				container, false);

		return rootView;
	}
	
	public static void setmyText(final String string) {
		Log.d("Prueba de mensajes", string);
		}
}
