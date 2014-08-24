package com.example.ourblackbox2;

import java.io.File;
import java.io.IOException;

import android.hardware.Camera;
import android.media.AudioManager;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.View;

public class BRecorder
{
	
	protected BIOstream biostream;
	protected MediaRecorder bRecorder;
	protected String filePath;	
	protected File videoFile;
	protected static String Path;
	//
	protected static int SECONDS_BETWEEN_VIDEO=15;//동영상 녹화 간격
	protected static int videoCurrentTime;//처음 시작 시간
	//
	protected boolean isRecording;//현재녹화중인지나타내는것
	protected boolean isVideotimerRunning;//비디오 타이머가 작동중인지 아닌지
	protected static boolean isTimeChange;
	
	public BRecorder()
	{
		videoFile=null;
		isRecording=false;
		isVideotimerRunning=false;
		isTimeChange=false;
		Path="";
		videoCurrentTime=0;
		biostream = new BIOstream();
		bRecorder= new MediaRecorder();
	}
	
	// 동영상촬영관련  메소드들.

	public void startRecorder()
	{	
		BSurfaceView.bSurface.setVisibility(View.VISIBLE);		
		Path=biostream.createFolder()+"/"+biostream.createName(System.currentTimeMillis());
		
		if(bRecorder==null){
			bRecorder= new MediaRecorder();
		}else{
			bRecorder.reset();
		}
		
		BSurfaceView.bSurface.getCamera().stopPreview();
		BSurfaceView.bSurface.getCamera().unlock();
		bRecorder.setCamera(BSurfaceView.bSurface.getCamera());
		
		bRecorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		bRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		
		bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_HIGH));
		
		//bRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		//bRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
		//bRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		bRecorder.setMaxDuration(50000);//최대캡쳐시간 50초
		bRecorder.setMaxFileSize(5000000);//최대파일크기 5메가
		
		bRecorder.setOutputFile(Path);
		bRecorder.setPreviewDisplay(BSurfaceView.bSurface.getSurfaceHolder().getSurface());
		
		//biostream.createVideoPath(System.currentTimeMillis());
		
		//AudioManager mAudioManager = (AudioManager) getSystemService(Context, AUDIO_SERVICE); 카메라 무음
	 	//mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM,0, 0);
		
		
		try{	
			bRecorder.prepare();
			bRecorder.start();
			Log.v("is playing?","really?="+20000);
		}catch(IllegalStateException e){
			e.printStackTrace();
			return;
		}catch(IOException e){
			e.printStackTrace();
			return;
		}catch(Exception e){
			e.printStackTrace();
			return;
		}	
		isRecording=true;
		isVideotimerRunning=true;

	}
	
	public void resetRecorder(){
	
		bRecorder.stop();
		bRecorder.reset();
		//registerVideo();
		isRecording=false;
		videoCurrentTime=0;
	}

	public void stopRecorder()
	{	
		bRecorder.stop();
		bRecorder.reset();
		//registerVideo()
		
		biostream.fileScan();
		isVideotimerRunning=false;
		videoCurrentTime=0;
		bRecorder=null;
		isRecording=false;
		BSurfaceView.bSurface.getCamera().lock();//카메라객체 잠금
		
	}
	
	public void destroyRecorder()
	{
		if(bRecorder !=null)
		{
			bRecorder.release();
			bRecorder=null;
		}
	}

}
	





    
    