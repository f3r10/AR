package com.fis.ra;


import com.fis.ra.R;
import com.fis.ra.GamesFragment.ButtonClickHandler;
import com.unity3d.player.UnityPlayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
 
public class MoviesFragment extends Fragment {
	Button btn; 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	
        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);
        btn = (Button)rootView.findViewById(R.id.buttonHidden);
        btn.setOnClickListener(new ButtonClickHandler());
        return rootView;
    }
    
    public class ButtonClickHandler implements View.OnClickListener {
		//When button is clicked
    	
		public void onClick(View view) {
			getActivity().getSupportFragmentManager().popBackStack();
		}
	}
 
}
