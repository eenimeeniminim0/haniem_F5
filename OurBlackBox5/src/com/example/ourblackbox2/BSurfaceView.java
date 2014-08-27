package com.example.ourblackbox2;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BSurfaceView extends SurfaceView implements SurfaceHolder.Callback
{
	SurfaceHolder bHolder;
	Camera bCamera;
	public static BSurfaceView bSurface;
		
	public BSurfaceView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		bHolder=getHolder();
		bHolder.addCallback(this);
	}
	
	
	public void surfaceCreated(SurfaceHolder holder)
	{
		bCamera=Camera.open();
		try{
			bCamera.setPreviewDisplay(bHolder);
			if(this.getResources().getConfiguration().orientation!=Configuration.ORIENTATION_LANDSCAPE){
				bCamera.setDisplayOrientation(90);
			}
		}catch(IOException e){
			bCamera.release();
			bCamera=null;
		}
	}
		
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		if(bCamera != null)
		{
			bCamera.stopPreview();
			bCamera.release();
			bCamera=null;
		}
	}
		
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) 
	{	
		Camera.Parameters params = bCamera.getParameters();
		params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE); 
		List<Size> arSize =params.getSupportedPreviewSizes();
		if(arSize==null)
		{
			params.setPreviewSize(width,height);
		}
		else
		{
			int diff=10000;
			Size opti=null;
			for(Size s:arSize)
			{
				if(Math.abs(s.height-height)<diff)
				{
					diff= Math.abs(s.height-height);
					opti=s;
				}
			}
			params.setPreviewSize(opti.width,opti.height);
		}
		
		bCamera.setParameters(params);
		bCamera.startPreview();
	}
	
	public Camera getCamera()
	{
		return this.bCamera;
	}
	
	public SurfaceHolder getSurfaceHolder()
	{
		return this.bHolder;
	}
	
	public void startAutofocus()
	{
		bCamera.autoFocus(bAutofocus);
	}
	
	AutoFocusCallback bAutofocus= new AutoFocusCallback(){
		public void onAutoFocus(boolean success, Camera camera){
			
		}
	};
	
	
}
