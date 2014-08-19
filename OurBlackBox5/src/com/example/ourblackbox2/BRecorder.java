package com.example.ourblackbox2;

import java.io.File;
import java.io.IOException;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class BRecorder
{
	
	BIOstream biostream;
	MediaRecorder bRecorder;
	String filePath;	
	File videoFile;
	boolean isRecording;//�����ȭ��������Ÿ���°�
	String Path;
	//
	static int SECONDS_BETWEEN_VIDEO=15;//������ ��ȭ ����
	int videoCurrentTime;//ó�� ���� �ð�
	//
	boolean isVideotimerRunning;//���� Ÿ�̸Ӱ� �۵������� �ƴ���
	
	//RecorderThread bThread;
	
	//private Thread videotimerUpdate;
	//private Handler videotimerUpdateHandler; 
	
	
	public BRecorder()
	{
		videoFile=null;
		isRecording=false;
		isVideotimerRunning=false;
		Path="";
		videoCurrentTime=0;
		biostream = new BIOstream();
		bRecorder= new MediaRecorder();
		//bThread=new RecorderThread();
	}
	
	// �������Կ�����  �޼ҵ��.

	public void startRecorder()
	{	
		BSurfaceView.bSurface.getBSurfaceView().setVisibility(View.VISIBLE);		
		Path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+biostream.createName(System.currentTimeMillis());
		
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
		
		bRecorder.setMaxDuration(50000);//�ִ�ĸ�Ľð� 50��
		bRecorder.setMaxFileSize(5000000);//�ִ�����ũ�� 5�ް�
		
		bRecorder.setOutputFile(Path);
		bRecorder.setPreviewDisplay(BSurfaceView.bSurface.getSurfaceHolder().getSurface());
		
		
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
		
		isVideotimerRunning=false;
		videoCurrentTime=0;
		bRecorder=null;
		isRecording=false;
		BSurfaceView.bSurface.getCamera().lock();//ī�޶�ü ���
		

		try {
			BSurfaceView.bSurface.getCamera().setPreviewDisplay(BSurfaceView.bSurface.getSurfaceHolder());//ī�޶�ü�� preview display�� �Ҵ���.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		BSurfaceView.bSurface.getCamera().startPreview();//ī�޶�ü���� �޾Ƶ��̴� ȭ�鿡 ���񽺺信 �����䰡 ���̴°� ����.
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
	





    
    