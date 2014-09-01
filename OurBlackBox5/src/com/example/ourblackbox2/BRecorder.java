package com.example.ourblackbox2;

import java.io.File;
import java.io.IOException;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.View;

public class BRecorder
{
	protected MediaRecorder bRecorder;
	protected String filePath;	
	protected File videoFile;
	protected BIOstream biostream;
	protected static String Path;
	protected static String File;
	//
	protected boolean isRecording;//현재녹화중인지나타내는것
	protected boolean isVideotimerRunning;//비디오 타이머가 작동중인지 아닌지

	
	public BRecorder()
	{
		videoFile=null;
		isRecording=false;
		isVideotimerRunning=false;
		Path="";
		File="";
		bRecorder= new MediaRecorder();
		biostream=new BIOstream();
	}
	
	// 동영상 설정 관련 메소드
	/*
	public void setQuality()
	{
		if(SettingActivity.isButtonPushed==true)
			bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_LOW));
		else
			bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_HIGH));
	}
	*/
	
	// 동영상 촬영 관련  메소드
	public void initRecorder()
	{
		BSurfaceView.bSurface.setVisibility(View.VISIBLE);	
		File=biostream.createFolder();
		Path=File+"/"+biostream.createName(System.currentTimeMillis());
		//biostream.pathSave(Path);
		
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
		
		bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_LOW));
		//setQuality();
		
		//bRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); //인코딩 어떻게 할건지
		//bRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
		//bRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		//bRecorder.setVideoFrameRate(24);
		//bRecorder.setVideoSize(720,480);
		
		bRecorder.setMaxDuration(60000);//최대캡쳐시간 60초
		bRecorder.setMaxFileSize(10000000);//최대파일크기 10메가
		
		bRecorder.setOutputFile(Path);
		bRecorder.setPreviewDisplay(BSurfaceView.bSurface.getSurfaceHolder().getSurface());
		
		//biostream.createVideoPath(System.currentTimeMillis());
		
	}
	
	
	
	public void startRecorder()
	{	
		
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
		//fileScan();
		//registerVideo();
		isRecording=false;
	}

	public void stopRecorder()
	{	
		bRecorder.stop();
		bRecorder.reset();
		//fileScan();
		//registerVideo();
		isVideotimerRunning=false;
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
	





    
    