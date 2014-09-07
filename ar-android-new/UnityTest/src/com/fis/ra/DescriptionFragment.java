package com.fis.ra;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DescriptionFragment extends Fragment {
	static String msg = "noHayNada";
	static TextView msgFromUnity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_description,
				container, false);
		msgFromUnity = (TextView) rootView.findViewById(R.id.txtUnity);
		msgFromUnity.setText(msg);

		return rootView;
	}

	public static void setmyText(final String string) {
		Log.d("Prueba de mensajes", string);
	}

	public static void setMsgUnity(String string) {
		// msgFromUnity.setText(string);
		msg = string;
		Log.d("settings", string);
	}
}
