package com.fis.ra;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class AboutFragment extends Fragment{
	
	private static String videoUri = null;
	 static Activity thisActivity = null;
	 @Override
	 
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 	
	        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
	        return rootView;
	    }

}
