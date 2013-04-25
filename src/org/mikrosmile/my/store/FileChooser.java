package org.mikrosmile.my.store;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ListView;

import com.htc.widget.ActionBarContainer;
import com.htc.widget.ActionBarExt;
import com.htc.widget.ActionBarItemView;
import com.htc.widget.ActionBarText;

@SuppressLint("SdCardPath")
public class FileChooser extends ListActivity {
    public static ActionBarItemView mActionButton;
    public static ActionBarText mActionText;
    private ActionBarExt actionBarExt=null;
    private ActionBarText actionBarText=null; 
    private ActionBarContainer actionBarContainer=null;
    private ActionBarItemView  actionBarItemView=null;
    public static final boolean USE_HTCSTYLE = true;
	FileArrayAdapter adapter;
    private File currentDir;
    
	 private void SetupActionBar()
	    {
		 	actionBarExt=new ActionBarExt(this,getActionBar());
	        actionBarExt.enableHTCLandscape(false);
	        actionBarContainer=actionBarExt.getCustomContainer();
	        actionBarText=new ActionBarText(this);    
	        actionBarItemView=new ActionBarItemView(this);		
		    actionBarContainer.setBackUpEnabled(true);		        
		    actionBarText.setPrimaryText(R.string.downloaded); 
		    actionBarContainer.addCenterView(actionBarText);
		    actionBarItemView.setIcon(R.drawable.ic_skins);
		    actionBarItemView.setText(R.string.skins);
		    actionBarContainer.addRightView(actionBarItemView);
		    actionBarContainer.setRightDividerEnabled(true);
		    actionBarContainer.setBackUpOnClickListener(new View.OnClickListener() 
          {
              public void onClick(View view) 
              {
                  finish();    	
              } 
          });
		    actionBarItemView.setOnClickListener(new android.view.View.OnClickListener() {
		    	public void onClick(View view)
		           {
		    		Intent intent = new Intent(Intent.ACTION_DEFAULT);
		           	intent.setClassName("com.htc.home.personalize", "com.htc.home.personalize.main.PersonalizeCarouselMainActivity");
		           	startActivity(intent);   
		           }
		    });
	    } 
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDir = new File("/sdcard/MyStore/Skins");
        fill(currentDir);
        SetupActionBar();
    }
    private void fill(File f)
    {
        File[]dirs = f.listFiles();
         List<Option>dir = new ArrayList<Option>();
         List<Option>fls = new ArrayList<Option>();
         try{
             for(File ff: dirs)
             {
                if(ff.isDirectory())
                    dir.add(new Option(ff.getName(),"Folder",ff.getAbsolutePath()));
                else
                {
                    fls.add(new Option(ff.getName(),"Location: "+ff.getParent(),ff.getAbsolutePath()));
                }
             }
         }catch(Exception e)
         {
             
         }
         Collections.sort(dir);
         Collections.sort(fls);
         dir.addAll(fls);
         if(!f.getName().equalsIgnoreCase("sdcard"))
         adapter = new FileArrayAdapter(FileChooser.this,R.layout.file_view,dir);
         this.setListAdapter(adapter);
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Option o = adapter.getItem(position);
        if(o.getData().equalsIgnoreCase("folder")||o.getData().equalsIgnoreCase("parent directory")){
                currentDir = new File(o.getPath());
                fill(currentDir);
        }
        else
        {
            onFileClick(o, position, id);
        }
    }
    private void onFileClick(final Option o, int position, long id)
    {
        {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/MyStore/Skins/" + o.getName())), "application/vnd.android.package-archive");
		startActivity(intent);  
				    }
			
        }
    }
