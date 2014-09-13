package com.example.ourblackbox2;

import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class BRecorder
{
	protected MediaRecorder bRecorder;
	protected String filePath;	
	protected File videoFile;
	protected BIOstream biostream;
	protected static String Path;
	protected static String Folder;
	protected static String Name;
	//
	protected boolean isRecording;//�����ȭ��������Ÿ���°�
	protected boolean isVideotimerRunning;//���� Ÿ�̸Ӱ� �۵������� �ƴ���
		
	
	public BRecorder()
	{
		videoFile=null;
		isRecording=false;
		isVideotimerRunning=false;
		Path="";
		Folder="";
		Name="";
		bRecorder= new MediaRecorder();
		biostream=new BIOstream();
	}
	
	// ������ ���� ���� �޼ҵ�
	public void setQuality()
	{
		if(BRecordingSetting.recQuality.equals("high"))
			bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_HIGH));
		
		else if(BRecordingSetting.recQuality.equals("normal"))
			bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_TIME_LAPSE_HIGH ));

		else
			bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_LOW));		
	}

	


	
	public void setStorageLocation()
	{
		if(BStorageSetting.storageLocation.equals("internal")){
			Folder=biostream.createInternalFolder();
			Name=biostream.createName(System.currentTimeMillis());
			Path=Folder+"/"+Name;
			Log.v("����޸𸮿�����ǳ���?","�ñ��մϴ�="+BStorageSetting.storageLocation);
			
		}
		else{
			Folder=biostream.createExternalFolder();
			Name=biostream.createName(System.currentTimeMillis());
			Path=Folder+"/"+Name;
			Log.v("����޸𸮿�����ǳ���?","�ñ��մϴ�="+BStorageSetting.storageLocation);
			
		}		
	}
	
	
	// ������ �Կ� ����  �޼ҵ�
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
		
		//bRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); //���ڵ� ��� �Ұ���
		//bRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
		//bRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		//bRecorder.setVideoFrameRate(24);
		//bRecorder.setVideoSize(720,480);
		
		bRecorder.setMaxDuration(60000);//�ִ�ĸ�Ľð� 60��
		bRecorder.setMaxFileSize(10000000);//�ִ�����ũ�� 10�ް�
		
		bRecorder.setOutputFile(Path);
		bRecorder.setPreviewDisplay(BSurfaceView.bSurface.getSurfaceHolder().getSurface());
		
		//biostream.createVideoPath(System.currentTimeMillis());
		
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
		Intent intent;
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
		BSurfaceView.bSurface.getCamera().lock();//ī�޶�ü ���
		
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
	





    
    
