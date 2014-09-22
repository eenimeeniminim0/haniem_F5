package com.example.ourblackbox2;

import java.io.IOException;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.util.Log;
import android.view.View;

public class BRecorder
{
	protected MediaRecorder bRecorder;	
	protected BIOstream biostream;
	protected static String Path;
	protected static String Folder;
	protected static String Name;
	//
	protected boolean isRecording;//현재녹화중인지나타내는것
	protected boolean isVideotimerRunning;//비디오 타이머가 작동중인지 아닌
	
	
	public BRecorder()
	{
		isRecording=false;
		isVideotimerRunning=false;
		Path="";
		Folder="";
		Name="";
		bRecorder= new MediaRecorder();
		biostream=new BIOstream();
	}
	
	// 동영상 설정 관련 메소드
	public void setQuality()
	{
		if(BRecordingSetting.recQuality.equals("high"))
			bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_HIGH));
		
		else if(BRecordingSetting.recQuality.equals("normal"))
			setDefaultProfile();

		else
			bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_LOW));		
	}
	
	public void setDefaultProfile()
	{
			if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_1080P))
				bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_1080P ));
			else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_720P))
				bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT,CamcorderProfile.QUALITY_720P ));
			else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_480P))
				bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_480P ));
			else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_LOW))
				bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_LOW ));
	}

	


	
	public void setStorageLocation()
	{
		if(BStorageSetting.storageLocation.equals("internal")){
			Folder=biostream.createInternalFolder();
			Name=biostream.createName(System.currentTimeMillis());
			Path=Folder+"/"+Name;
			Log.v("내장메모리에저장되나용?","궁금합니당="+BStorageSetting.storageLocation);
			
		}
		else{
			Folder=biostream.createExternalFolder();
			Name=biostream.createName(System.currentTimeMillis());
			Path=Folder+"/"+Name;
			Log.v("외장메모리에저장되나용?","궁금합니당="+BStorageSetting.storageLocation);
			
		}		
	}
	
	
	// 동영상 촬영 관련  메소드
	public void initRecorder()
	{
		BSurfaceView.bSurface.setVisibility(View.VISIBLE);	
		setStorageLocation();
		
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
		
		setQuality();
		
		bRecorder.setMaxDuration(60000);//최대캡쳐시간 60초 고치기
		bRecorder.setOutputFile(Path);
		bRecorder.setPreviewDisplay(BSurfaceView.bSurface.getSurfaceHolder().getSurface());
		
	}
	
	public void startRecorder()
	{	
		
		try{	
			bRecorder.prepare();
			bRecorder.start();
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
		isRecording=false;
	}

	public void stopRecorder()
	{	
		bRecorder.stop();
		bRecorder.reset();
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
	





    
    
