package com.pgmacdesign.speechtotextclipboardwidget;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

public class SadPanda extends Activity{

	MediaPlayer aww;
	
	public void theSadPanda(Context context, String string){
	
		Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
		aww = MediaPlayer.create(context, R.raw.aww_sound_effect);
		aww.start();
		
	}

}
