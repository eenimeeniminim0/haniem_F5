package com.example.ourblackbox2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BIOstream  {
	
	
	String createName(long dateTaken){
		Date date= new Date(dateTaken);
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
		return dateFormat.format(date)+".mp4";
	}
	
/*	private void registerVideo(File videoFile){

		ContentValues values = new ContentValues();
		values.put(Video.Media.TITLE, "OurVideo");
		values.put(Video.Media.DISPLAY_NAME, videoFile.getName());
		values.put(Video.Media.DATE_TAKEN, System.currentTimeMillis());
		values.put(Video.Media.MIME_TYPE, "video/mp4");
		values.put(Video.Media.DATA, videoFile.getPath());
		values.put(Video.Media.SIZE, videoFile.length());
		this.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values); // 액티비티에서만 사용가능
		Toast.makeText(this, "video saved", Toast.LENGTH_SHORT).show();	
		
	}*/
	
	
}
