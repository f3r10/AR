package com.fis.ra;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

public class VideoFragment extends Fragment implements SurfaceHolder.Callback,
		MediaPlayer.OnPreparedListener, VideoControllerView.MediaPlayerControl {
	SurfaceView videoSurface;
	static MediaPlayer player;
	VideoControllerView controller;
	private View rootView;
	private static Context context;
	
	private static String stringVideoPath ="Video/big_buck_bunny.mp4";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		context = getActivity();
		rootView = inflater.inflate(R.layout.fragment_video, container, false);
		videoSurface = (SurfaceView) rootView.findViewById(R.id.videoSurface);
		SurfaceHolder videoHolder = videoSurface.getHolder();
		videoHolder.addCallback(this);

		player = new MediaPlayer();
		controller = new VideoControllerView(getActivity()
				.getApplicationContext());

		try {
			AssetFileDescriptor descriptor = getActivity().getAssets()
                    .openFd(stringVideoPath);
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
			descriptor.close();
			player.setOnPreparedListener(this);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		rootView.setOnTouchListener(new View.OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					controller.show();
					return false;
				}
				return true;
			}
		});
		return rootView;
	}

	public static void setmyText(final String string) {
		Log.d("Prueba de mensajes", string);
	}

	public static void setVideoPath(final String path) {
		stringVideoPath = path;
	}
	public static void setPauseVideo() {
		Log.d("mediaController", "pause video");
		player.pause();
	}

	public static int getCurrentPostion() {
		return player.getCurrentPosition();
	}

	// Implement SurfaceHolder.Callback
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		player.setDisplay(holder);
		player.prepareAsync();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	// End SurfaceHolder.Callback

	// Implement MediaPlayer.OnPreparedListener
	@Override
	public void onPrepared(MediaPlayer mp) {
		controller.setMediaPlayer(this);
		controller.setAnchorView((FrameLayout) rootView
				.findViewById(R.id.videoSurfaceContainer));
		
		if(!player.isPlaying()){
			player.start();
		}
		
	}

	// End MediaPlayer.OnPreparedListener

	// Implement VideoMediaController.MediaPlayerControl
	@Override
	public boolean canPause() {
		return true;
	}

	@Override
	public boolean canSeekBackward() {
		return true;
	}

	@Override
	public boolean canSeekForward() {
		return true;
	}

	@Override
	public int getBufferPercentage() {
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		return player.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		return player.getDuration();
	}

	@Override
	public boolean isPlaying() {
		return player.isPlaying();
	}

	@Override
	public void pause() {
		player.pause();
	}

	@Override
	public void seekTo(int i) {
		player.seekTo(i);
	}

	@Override
	public void start() {
		player.start();
	}

	@Override
	public boolean isFullScreen() {
		return false;
	}

	@Override
	public void toggleFullScreen() {

	}

	// End VideoMediaController.MediaPlayerControl

	public static Context getCustomAppContext() {
		return context;
	}

	@Override
	public void onPause() {
		player.stop();
		player.release();
		super.onPause();
	}

}
