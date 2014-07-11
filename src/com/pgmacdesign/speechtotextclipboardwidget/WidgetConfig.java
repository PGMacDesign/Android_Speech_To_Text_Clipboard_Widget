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

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RemoteViews;

public class WidgetConfig extends Activity {
	
	//Define these as global variables
	int awID;
	AppWidgetManager awm;
	Context c;
	
	//Doing this part within an onCreate, but can be done elsewhere
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.widget);

		c = WidgetConfig.this;
		
		//An intent is opening this class, therefore, must make one
		Intent i = getIntent();
		
		//Create a bundle since info is being passed around (Which app launched this activity)
		Bundle extras = i.getExtras();
		
		//As long as the extras had something, setup the app widget id
		if (extras != null){
			//Get an ID and pass it in. IE, a way to checking which widget activated this class
			awID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
			//This returns 1 App widget ID
		} else {
			//In case something gets a-broken!
			finish();
		}
		
		awm = AppWidgetManager.getInstance(c); 	
	
		createTheWidget(); //You may want to put this as a button instead of inside the onCreate. 
	}
	
	//This creates the actual widget
	public void createTheWidget(){
		
		//If you need to get info from EditText windows, do so first. 
		
		//Setup a remoteview referring to the context and relating to the widget
		RemoteViews v1 = new RemoteViews(c.getPackageName(), R.layout.widget);
		
		//Set the remote view (remote meaning on the homescreen widget) to any TextViews setup within the widget.xml (AKA NAME_OF_WIDGET_XML_LAYOUT_YOU_ARE_USING)
		//v1.setTextViewText(R.id.text_view_config_input, "String and whatnot");
		
		//This intent opens a class when clicked. Again, note C for context
		Intent intent = new Intent(c, Splash.class);
		
		//A pending intent is also needed. Again, note the C for context
		PendingIntent pendingIntent = PendingIntent.getActivity(c, 0, intent, 0);
		
		//If you have a button within the widget icon, link it to an ID here
		v1.setOnClickPendingIntent(R.id.button_widget_open, pendingIntent);	
		
		//Update the widget with the remote view
		awm.updateAppWidget(awID, v1);
		
		//Lastly, need to set a result
		Intent result = new Intent();
		
		//Updating the ID that is being called
		result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, awID);
		
		//Confirm the result works then set it
		setResult(RESULT_OK, result);
		
		//We want this to finish. Might be smart to include this when the button is clicked so that the user can choose when to end it
		finish();
	}
}