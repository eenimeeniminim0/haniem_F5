package com.example.ourblackbox2;

import java.io.File;
import java.io.IOException;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class BRecorder extends MediaRecorder
{
	
	BIOstream biostream;
	BRecorder bRecorder;
	String filePath;	
	File videoFile;
	boolean isRecording;//�����ȭ��������Ÿ���°�
	String Path;
	//
	static int SECONDS_BETWEEN_VIDEO=15;//������ ��ȭ ����
	int videoCurrentTime;//ó�� ���� �ð�
	//
	boolean isVideotimerRunning;//���� Ÿ�̸Ӱ� �۵������� �ƴ���
	
	
	public BRecorder()
	{
		videoFile=null;
		isRecording=false;
		isVideotimerRunning=false;
		Path="";
		videoCurrentTime=0;
		biostream = new BIOstream();
	}
	
	// �������Կ�����  �޼ҵ��.

	public void startRecorder(BSurfaceView bSurface)
	{	
		bSurface.getBSurfaceView().setVisibility(View.VISIBLE);		
		Path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+biostream.createName(System.currentTimeMillis());
		
		if(bRecorder==null){
			bRecorder= new BRecorder();
		}else{
			reset();
		}
		
		bSurface.getCamera().stopPreview();
		bSurface.getCamera().unlock();
		setCamera(bSurface.getCamera());
		
		setVideoSource(MediaRecorder.VideoSource.DEFAULT);
		setAudioSource(MediaRecorder.AudioSource.DEFAULT);
		
		setProfile(CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_HIGH));
		
		//bRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
		//bRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
		//bRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		
		setMaxDuration(50000);//�ִ�ĸ�Ľð� 50��
		setMaxFileSize(5000000);//�ִ�����ũ�� 5�ް�
		
		setOutputFile(Path);
		setPreviewDisplay(bSurface.getSurfaceHolder().getSurface());
		
		
		try{	
			prepare();
			start();
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
	
		//registerVideo();
		reset();
		isRecording=false;
		videoCurrentTime=0;
	}

	public void stopRecorder(BSurfaceView bSurface)
	{	
		stop();
		reset();
		//registerVideo
		videoCurrentTime=0;
		bRecorder=null;
		isRecording=false;
		bSurface.getCamera().lock();//ī�޶�ü ���

		try {
			bSurface.getCamera().setPreviewDisplay(bSurface.getSurfaceHolder());//ī�޶�ü�� preview display�� �Ҵ���.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		bSurface.getCamera().startPreview();//ī�޶�ü���� �޾Ƶ��̴� ȭ�鿡 ���񽺺信 �����䰡 ���̴°� ����.
	}
	
	public void destroyRecorder()
	{
		if(bRecorder !=null)
		{
			release();
			bRecorder=null;
		}
	}
	
	/*public boolean getIsRecording()
	{
		return this.isRecording;
	}*/
	

}


    