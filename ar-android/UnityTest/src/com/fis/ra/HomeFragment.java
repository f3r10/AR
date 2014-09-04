package com.fis.ra;

import com.qualcomm.QCARUnityPlayer.QCARPlayerSharedActivity;
import com.unity3d.player.UnityPlayer;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class HomeFragment extends Fragment {
	
	private QCARPlayerSharedActivity mQCARShared;
    public static TextView textoUnity;
    static public int variableInt=0;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        final View rootView = inflater.inflate(R.layout.fragment_games, container, false);
        final UnityPlayer mUnityPlayer = new UnityPlayer(getActivity());
        final int mode = mUnityPlayer.getSettings().getInt("gles_mode", 1);  
        this.mQCARShared = new QCARPlayerSharedActivity();
        this.mQCARShared.onCreate(getActivity(), mode, new QCARPlayerSharedActivity.IUnityInitializer() {
           
        	
        	@Override
            public void InitializeUnity() {
                mUnityPlayer.init(mode, false);
               FrameLayout container = (FrameLayout)rootView.findViewById(R.id.unityLayout);
                FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                container.addView(mUnityPlayer, 0, lp);
        
                mUnityPlayer.windowFocusChanged(true);
                mUnityPlayer.resume();
                
              //boton para llamar a unity
                //final Button btnUnity=(Button)rootView.findViewById(R.id.buttonUnity);
                //btnUnity.setOnClickListener(new ButtonClickHandler());
                
                //textview que se modifica desde unity con Set
                textoUnity=(TextView)rootView.findViewById(R.id.textViewUnity);
            }
        });
        return rootView;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    
    public class ButtonClickHandler implements View.OnClickListener {
		//When button is clicked
    	
		public void onClick(View view) {
		//llamar a metodo unity 
			UnityPlayer.UnitySendMessage("ImageTarget", "GetValor", "");
			Log.d("android", "llamando a GetValor desde android");
			//Log.d("android","valor de resultadoSuma: "+resultadoSuma);
			variableInt+=5;
		}
	}
    
    static public void CambiarTexto()
    {
    	Log.d("android", "llamando a CambiarTexto desde unity");
    	
    	textoUnity.setText(String.valueOf(variableInt));
    
    }
    
    static public int getValorInt()
    {
    	return variableInt;
    }

}
