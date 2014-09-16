package com.fis.ra;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HelpFragment extends Fragment {
	static String msg = "" +
			"Con LookARte las visitas a los museos van a ser mas divertidas, con esta aplicación podras escuchar audio, ver videos que complementaran a la información del museo.\n" +
			"Tan solo tienes que enfocar con el celular al objeto del museo que quieras tener mas información y al instante podras disfrutar de divertidos video, e inclusive podras jugar";
	static TextView msgFromUnity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_help,
				container, false);
		msgFromUnity = (TextView) rootView.findViewById(R.id.txtUnity);
		msgFromUnity.setText(msg);

		return rootView;
	}
}
