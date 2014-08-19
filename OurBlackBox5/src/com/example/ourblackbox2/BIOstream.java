package com.example.ourblackbox2;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import android.os.Environment;
import android.provider.MediaStore.Video;
import android.text.format.DateFormat;
import android.util.Log;

public class BIOstream  {
	
	private String videoFileName = null;	
	private ContentValues videoValues = null;
	String dir = null;
	
	
	public String createName(long dateTaken){
		Date date= new Date(dateTaken);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
		return dateFormat.format(date)+".mp4";
	}
	
	public String createFolder(){
		
		String dir=Environment.getExternalStorageDirectory().getPath() + "/OurBlackBox";
		File folder = new File(dir);
		folder.mkdirs();
		return dir;
    	
	}
	
	void createVideoPath(long dateTaken) {
		String dirName = "OurBlackBox";
		File cameraDir = new File(dirName);
    	cameraDir.mkdirs();
		String title = createName(dateTaken);
		String filename = title;
		ContentValues values = new ContentValues(8);
		values.put(Video.Media.TITLE, title);
		values.put(Video.Media.DISPLAY_NAME, title + ".mp4");
		values.put(Video.Media.DATE_TAKEN, dateTaken);
		values.put(Video.Media.MIME_TYPE, "video/mp4");
		values.put(Video.Media.DATA, filename);
		videoFileName = filename;
		videoValues = values;
	}
	
	
}
