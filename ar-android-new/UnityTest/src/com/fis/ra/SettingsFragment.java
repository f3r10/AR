package com.fis.ra;



import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SettingsFragment extends Fragment {
	private Button btnEspaniol;
	private Button btnIngles;
	private Button btnQuechua;
	Fragment fragment = null;
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	  
	        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
	        btnEspaniol = (Button)rootView.findViewById(R.id.btnEspaniol);
	        btnIngles = (Button)rootView.findViewById(R.id.btnIngles);
	        btnQuechua = (Button)rootView.findViewById(R.id.btnQuechua);
	        //btn.setOnClickListener(new ButtonClickHandler());
	        //UnityPlayer.UnitySendMessage("SetSingletonLanguage", "NameARObjec", "idioma/spa, eng, que");
	        return rootView;
	        
	        
	    }
	 
	 /*
	 public class ButtonClickHandler implements View.OnClickListener {
			//When button is clicked
			public void onClick(View view) {
				
				//fragment = new NotificationsFragment();
				
				fragment = new AboutFragment();
				AboutFragment.setVideoUri("http://people.xiph.org/~maikmerten/demos/BigBuckBunny.ogv");
				String saludo = "Hola";
				//((NotificationsFragment) fragment).setmyText(saludo);
				if (fragment != null) {
					FragmentManager fragmentManager = getFragmentManager();
					fragmentManager.beginTransaction()
							.replace(R.id.fragment_detail, fragment).commit();

					// Update selected item and title, then close the drawer
					
				} else {
					// Log error
					Log.e("MainActivity", "Error in creating fragment");
				}
				
				
			}
			}*/
}
