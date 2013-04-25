package org.mikrosmile.my.store;

import com.htc.widget.ActionBarContainer;
import com.htc.widget.ActionBarExt;
import com.htc.widget.ActionBarText;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity {
	private ActionBarExt actionBarExt=null;
	private ActionBarText actionBarText=null;    
	private ActionBarContainer actionBarContainer=null;
	
	private void SetupActionBar() {
        actionBarExt=new ActionBarExt(this,getActionBar());
        actionBarExt.enableHTCLandscape(false);
        actionBarContainer=actionBarExt.getCustomContainer();
        actionBarText=new ActionBarText(this);    	        
	    actionBarText.setPrimaryText("Settings");   
	    actionBarContainer.addCenterView(actionBarText);
    } 
	
	@SuppressWarnings("deprecation")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SetupActionBar();
		addPreferencesFromResource(R.xml.prefs);
	}

}
