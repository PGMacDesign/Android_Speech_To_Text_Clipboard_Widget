/*
        Copyright (C) <2014>  <Patrick Gray MacDowell>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.pgmacdesign.speechtotextclipboardwidget;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.widget.Toast;

public class SpeechToText extends Activity
{
    private static final String TAG = "VoiceRecognitionStarterActivity";
    private int SPEECH_REQUEST_CODE = 1;

    @Override
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    sendRecognizeIntent();
	}
	
	private void sendRecognizeIntent()
	{
	    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
	    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak When You Hear The Beep");
	    intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 10);
	    startActivityForResult(intent, SPEECH_REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	    if (requestCode == SPEECH_REQUEST_CODE)
	    {
	        if (resultCode == RESULT_OK) {
	            L.m("result ok");
	            
	            //COPY TO CLIPBOARD HERE
	            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
	            String uberString = "";
				for (int i = 0; i< results.size(); i++){
					uberString = results.get(0).toString();
					L.m(results.get(i).toString()); //May need to attach these to a list to click the best translation 
				}
				L.m(uberString);
				
				//Copy the text to the clipboard
				ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE); 
				ClipData clip = ClipData.newPlainText("data", uberString);
				clipboard.setPrimaryClip(clip);
				
				//make a toast here
				Toast.makeText(this, "Text Copied -- " + uberString, Toast.LENGTH_LONG).show();
	            
	            finish();   
	         } else {
	            L.m("result NOT ok");
	            finish();
	        }
	
	    }
	
	    super.onActivityResult(requestCode, resultCode, data);
	    }
	
	}