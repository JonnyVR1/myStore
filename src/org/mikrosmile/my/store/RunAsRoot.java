package org.mikrosmile.my.store;

import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;

public class RunAsRoot extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}
	
	public static void RunSu(String[] cmds) throws IOException{
        Process p = Runtime.getRuntime().exec("su");
        DataOutputStream os = new DataOutputStream(p.getOutputStream());            
        for (String tmpCmd : cmds) {
                os.writeBytes(tmpCmd+"\n");
        }           
        os.writeBytes("exit\n");  
        os.flush();
}

}
