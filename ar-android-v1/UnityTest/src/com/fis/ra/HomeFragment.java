package com.fis.ra;

import java.io.IOException;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.qualcomm.QCARUnityPlayer.QCARPlayerSharedActivity;
import com.unity3d.player.UnityPlayer;


public class HomeFragment extends Fragment implements MediaPlayer.OnPreparedListener {
	
	public static QCARPlayerSharedActivity mQCARShared;
    static Activity thisActivity = null;
    static Fragment fragment = null;
    static HomeFragment instance;
    private static Context context;
    static MediaPlayer audioInfo;
    static Toast toast;
    private static String stringAudioPath ="Audio/robin.mp3";
    
    private static DialogoConfirmacion dialog = new DialogoConfirmacion();
    
    //public static Context mContext;
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		context=getActivity();
		audioInfo = MediaPlayer.create(HomeFragment.getCustomAppContext(),R.raw.robin);
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        final UnityPlayer mUnityPlayer = new UnityPlayer(getActivity());
        final int mode = mUnityPlayer.getSettings().getInt("gles_mode", 1);  
        HomeFragment.mQCARShared = new QCARPlayerSharedActivity();
        //audio
        
        /*audioInfo = new MediaPlayer();
        
        try {
			AssetFileDescriptor descriptor = getActivity().getAssets()
                    .openFd(stringAudioPath);
			audioInfo.setAudioStreamType(AudioManager.STREAM_MUSIC);
			audioInfo.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
			descriptor.close();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
        //Toast.makeText(getActivity(),"Enfoca un objeto",Toast.LENGTH_SHORT).show();
        toast = Toast.makeText(((Activity) HomeFragment.getCustomAppContext()),"Enfoca un objeto",Toast.LENGTH_SHORT);
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
                //textoUnity=(TextView)rootView.findViewById(R.id.textViewUnity);
               
            }
        });
        thisActivity = getActivity();
        //mContext=getActivity().getApplicationContext();
        return rootView;
    }
	
	
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    
    public static Context getCustomAppContext(){
        return context;
    }
    
    
    @Override // research 
    public void onActivityCreated(Bundle saved) { 
        super.onActivityCreated(saved);
        
    }
    /*  IS NESCESARY?
    public class ButtonClickHandler implements View.OnClickListener {
		//When button is clicked
    	
		public void onClick(View view) {
		//llamar a metodo unity 
			UnityPlayer.UnitySendMessage("ImageTarget", "GetValor", "");
			Log.d("android", "llamando a GetValor desde android");
			//Log.d("android","valor de resultadoSuma: "+resultadoSuma);
			//variableInt+=5;
		}
	}
    
    */
    
    //UNITY BUTTON METHODS, don't change method name
    
    public static void DescriptionButton(String message){
    	Log.d("Android","ANDROID: DescriptionButton HA RECIBIDO EL STRING: "+message);
    	
    	fragment = new DescriptionFragment();
    	
		if (fragment != null) {
			FragmentManager fragmentManager = ((Activity) HomeFragment.getCustomAppContext()).getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_detail, fragment).commit();
			
		} else {
			Log.e("HomeFragment", "Error in creating Descriptionfragment");
		}
    }
    
    public static void AudioButton(String message){
    	Log.d("Android","ANDROID: DescriptionButton HA RECIBIDO EL STRING: "+message);
    	
		// When 'create' returns the MediaPlayer is already in the prepared
		// state and ready to go!
    	
    	if(audioInfo.isPlaying()){
    		audioInfo.stop();
    		
    		//audioInfo.release();
    	}else{
    		audioInfo = new MediaPlayer();
            try {
    			AssetFileDescriptor descriptor = HomeFragment.getCustomAppContext().getAssets()
                        .openFd(stringAudioPath);
    			audioInfo.setAudioStreamType(AudioManager.STREAM_MUSIC);
    			audioInfo.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
    			descriptor.close();
    			audioInfo.prepare();
    			
    		} catch (IllegalArgumentException e) {
    			e.printStackTrace();
    		} catch (SecurityException e) {
    			e.printStackTrace();
    		} catch (IllegalStateException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            
            audioInfo.start();
    		
    	}
    	
    	/*
    	fragment = new AudioFragment();
    	AudioFragment.setmyText(message);
		if (fragment != null) {
			FragmentManager fragmentManager = ((Activity) HomeFragment.getCustomAppContext()).getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_detail, fragment).commit();
			
		} else {
			Log.e("HomeFragment", "Error in creating AudioFragment");
		}	
		*/   	
    }
    
    public static void ImageButton(String message){
    	Log.d("Android","ANDROID: ImageButton HA RECIBIDO EL STRING: "+message);
    	fragment = new ImagesFragment();
    	ImagesFragment.setmyText(message);
		if (fragment != null) {
			FragmentManager fragmentManager = ((Activity) HomeFragment.getCustomAppContext()).getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_detail, fragment).commit();
			
		} else {
			Log.e("HomeFragment", "Error in creating ImageFragment");
		}	   	
    }
    
    public static void VideoButton(String message){
    	Log.d("Android","ANDROID: DescriptionButton HA RECIBIDO EL STRING: "+message);
    	
    	audioInfo.stop();
    	audioInfo.release();
    	fragment = new VideoFragment();
    	VideoFragment.setmyText(message);
		if (fragment != null) {
			FragmentManager fragmentManager = ((Activity) HomeFragment.getCustomAppContext()).getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.fragment_detail, fragment).commit();
			
		} else {
			Log.e("HomeFragment", "Error in creating VideoFragment");
		}   	
    }
    
    public static void GameButton(String message){
    	
    }
    
    //GAME BUTTON HANDLED BY UNITY
    
    
    //MESSAGE METHODS
    
    public static void StatusRecognitionStart(){
    	Log.d("Android","TOAST CREADO HOMEFRAGMENT statusRecognition");
    	
    	//Toast.makeText(((Activity) HomeFragment.getCustomAppContext()),"Enfoca un objeto",Toast.LENGTH_SHORT).show();
    	//toast.show();
    	toast.setText("Enfoca a un objeto para ver sus datos");
    	toast.show();
    }
    
    public static void StatusRecognitionStart(String status){
    	Log.d("Android","TOAST CREADO HOMEFRAGMENT statusRecongnition with param");
    	
    	toast.setText("TOAST CREADO HOMEFRAGMENT statusRecognition with param");
    	toast.show();
    	//Toast.makeText(((Activity) HomeFragment.getCustomAppContext()),"Enfoca un objeto",Toast.LENGTH_SHORT).show();

    }
     
    public static void SetMultimedia(String NameARObjec, String LanguageInterface, String DescriptionText, int NumberAudios, int NumberGames, String PathImages, int NumberVideos )
    {
    	Log.d("Android","DATOS RECIBIDOS CORRECTAMENTE");
    		//Toast.makeText(thisActivity,"Multimedia" ,Toast.LENGTH_SHORT).show();
    	//falta generar los paths multimedia
    	
    	//Description:
    	DescriptionFragment.setMsgUnity(DescriptionText);
    	
    	// Video
    	String stringVideo = "Video/" + NameARObjec+ "_video.mp4";
    	VideoFragment.setVideoPath(stringVideo);
    	Log.d("Audio", NameARObjec);
    	// Audio en el mismo fragment del Unity
    	stringAudioPath = "Audio/"+NameARObjec+"_audio.mp4";
    	// Set image path
    	String[] allImages = PathImages.split("-");
    	
    	Log.d("Images","Set images path");
    	Log.d("Images",PathImages);
    	ImagesFragment.setImagePath(allImages);
    	
    }
    
    public static void  ShowDialogLoadDataNewObject(String arNameObject){
		// dialogo de aceptar/cancelar llamar al metodo 
	// para juegos dos de perder el juego 
	Log.d("Android","TOAST CREADO HOMEFRAGMENT");
	//send message to unity
	dialog.setTrackableFound(arNameObject);
	// DIALOG!!!
	//toast.setText("Objeto "+arNameObject+" reconocido. Desea cargar la informacion de este objeto?");
	//toast.show();
	dialog.show(((Activity) HomeFragment.getCustomAppContext()).getFragmentManager(), "dialog");
}

	
	public static void  ShowToastTrackableFound(String trackableFound){
		
		//Toast.makeText(thisActivity,"ShowToastTrackableFound"+ trackableFound ,Toast.LENGTH_SHORT).show();
		Log.d("Android","TOAST CREADO HOMEFRAGMENT");
		
		toast.setText("Objeto "+trackableFound+" reconocido");
		toast.show();
	}
	
	public static void  ShowToastRecognizedSameObject(){
		
		//Toast.makeText(thisActivity,"RecognizedSameObject" ,Toast.LENGTH_SHORT).show();
		Log.d("Android","TOAST CREADO HOMEFRAGMENT");
		//Toast.makeText(((Activity) HomeFragment.getCustomAppContext()),"Enfoca un objeto",Toast.LENGTH_SHORT).show();
		
		toast.setText("Se ha reconocido el mismo objeto");
		toast.show();
	
	}
	
	public static void  ShowToastTrackingLost(String message){
		
		//Toast.makeText(thisActivity,"ShowToastTrackingLost" ,Toast.LENGTH_SHORT).show();
		Log.d("Android","TOAST CREADO HOMEFRAGMENT");
		
		toast.setText(message);
		toast.show();
		
	
	}
    
    @Override
    
    public void onResume(){
    	mQCARShared.onResume();
    	super.onResume();
    }
    
    @Override
    public void onPause(){
    	mQCARShared.onPause();
    	super.onPause();
    	
    }
    
    
    @Override
    public void onDestroy(){
    	//mQCARShared.onDestroy();
    	super.onDestroy();
    }


	@Override
	public void onPrepared(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		audioInfo.start();
	}
    
    
    
    
    
}
