package com.example.ourblackbox2;

import android.content.Intent;
import android.net.Uri;

public class BFileScan {
	
	public Intent fileScan()
	{
		//Intent intent =new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE); //�н� ������ �� Ŭ��������!!
		Intent intent =new Intent(Intent.ACTION_MEDIA_MOUNTED); 
		Uri uri= Uri.parse("file://"+BRecorder.Path);
		intent.setData(uri);
		return intent;
		
		//sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
		
	}
}
