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
	protected boolean isRecording;//�����ȭ��������Ÿ���°�
	protected boolean isVideotimerRunning;//���� Ÿ�̸Ӱ� �۵������� �ƴ���

	
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
	
	// ������ ���� ���� �޼ҵ�
	/*
	public void setQuality()
	{
		if(SettingActivity.isButtonPushed==true)
			bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_LOW));
		else
			bRecorder.setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_HIGH));
	}
	*/
	
	// ������ �Կ� ����  �޼ҵ�
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
	





    
    