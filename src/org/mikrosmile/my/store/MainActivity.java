package org.mikrosmile.my.store;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.htc.widget.ActionBarContainer;
import com.htc.widget.ActionBarExt;
import com.htc.widget.ActionBarItemView;
import com.htc.widget.ActionBarText;
import com.htc.widget.HtcAlertDialog;

public class MainActivity extends Activity {
	final static String AUTHORITY = "org.mikrosmile.my.store.MainActivity";
	private Carousel mCarousel = null;
	public static ActionBarItemView mActionButton;
    public static ActionBarText mActionText;
    public static final boolean USE_HTCSTYLE = true;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;
    String versionName = null;
	
	
	@Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("ContextMenu");
    }
	
	@Override
    public boolean onCreateOptionsMenu (Menu menu) {
        menu.add(1, 1, 1, "Settings");
        return true;
    }
	public boolean onOptionsItemSelected(MenuItem menuitem)
    {
    	boolean flag = true;
        switch (menuitem.getItemId())
        {
        case 1:
        	startActivity(new Intent(this, Settings.class));
        	break;
        }
		return flag;
    }
	
	private void SetupActionBar()
    {
        Object obj = new ActionBarExt(this, getActionBar());
        ((ActionBarExt)obj).setFullScreenEnabled(true);
        ((ActionBarExt)obj).enableHTCLandscape(false);
        mActionText = new ActionBarText(this);
        mActionText.setPrimaryText(getString(R.string.app_name) + " v" + versionName );
        mActionText.setSecondaryText("Based on myStore");
        obj = ((ActionBarExt)obj).getCustomContainer();
        ((ActionBarContainer)obj).setRightDividerEnabled(true);
        ((ActionBarContainer)obj).addCenterView(mActionText);
       mActionButton = new ActionBarItemView(this);
        mActionButton.setIcon(R.drawable.xda);
      mActionButton.setText(R.string.xda);
       ((ActionBarContainer)obj).addRightView(mActionButton);
       
       mActionButton.setOnClickListener(new android.view.View.OnClickListener() {

           public void onClick(View view)
           {
        	   
                 startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://forum.xda-developers.com/showthread.php?t=2230067")));

        	   
           }
           }
   );
    }
	
	public void onCreate(Bundle savedInstanceState) {
		try {
			@SuppressWarnings("unused")
			Process p = Runtime.getRuntime().exec("su");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        super.onCreate(savedInstanceState);
        File file = new File("/system/myStore.txt");
        if(!file.exists()){
        	HtcAlertDialog.Builder aDialog = new HtcAlertDialog.Builder(MainActivity.this);
         	aDialog.setCancelable(false);
         	aDialog.setTitle(R.string.error);
         	aDialog.setIcon(R.drawable.ic_alert);
         	aDialog.setMessage(R.string.error_msg);
         	 aDialog.setPositiveButton(R.string.exit, new DialogInterface.OnClickListener() {
         	    public void onClick(DialogInterface dialog,int which) {
         	    	finish();
                	
         	    }
         	  });
             
              aDialog.show();
        }
        requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        SetupActionBar();
        final int rootId = 1;
        FrameLayout viewRoot = new FrameLayout(this);
        viewRoot.setId(rootId);
        setContentView(viewRoot);
        mCarousel = new Carousel();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(rootId, mCarousel);
        ft.commit();
        registerForContextMenu(viewRoot);
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
        	Toast.makeText(getApplicationContext(), R.string.internet_connected, Toast.LENGTH_LONG).show();
        }else {
         	HtcAlertDialog.Builder aDialog = new HtcAlertDialog.Builder(MainActivity.this);
         	aDialog.setCancelable(false);
         	aDialog.setTitle(R.string.network);
         	aDialog.setIcon(R.drawable.ic_alert);
         	aDialog.setMessage(R.string.network_msg);
         	 aDialog.setPositiveButton(R.string.open_settings, new DialogInterface.OnClickListener() {
         	    public void onClick(DialogInterface dialog,int which) {
                	Intent intent = new Intent(Intent.ACTION_MAIN);
                	intent.setClassName("com.android.settings", "com.android.settings.wifi.WifiSettings");
                	startActivity(intent);
         	    }
         	  });
             aDialog.setNegativeButton(R.string.exit, new DialogInterface.OnClickListener() {
                 public void onClick(DialogInterface dialog, int which) {
                	 finish();
                }
             });
              aDialog.show();

        } 
	}

	
	
}
