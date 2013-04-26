package org.mikrosmile.my.store;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.htc.app.HtcProgressDialog;
import com.htc.widget.HtcAlertDialog;

public class Kernels extends Fragment implements
OnClickListener, OnItemClickListener {
	Boolean isInternetPresent = false;
    ConnectionDetector cd;
	private HtcProgressDialog mProgressDialog;
	String unzipLocation = Environment.getExternalStorageDirectory() + "/MyStore/Kernels/";
	String zipFile = Environment.getExternalStorageDirectory() + "/MS_kernels.zip";
    Button button;
    ListView listView;
    List<News> newsview;
    KernelsListView listViewAdapter;
    String URL = "";
    
    public Kernels() {      
    }
    
    @SuppressWarnings("static-access")
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
                             Bundle savedInstanceState) { 
		View view =  inflater.inflate(R.layout.kernels_activity, container, false);
		URL = cd.getUrls("ro.kernels");
		button = (Button) view.findViewById(R.id.button);
        listView = (ListView) view.findViewById(R.id.modsList);
        button.setOnClickListener(this);
        cd = new ConnectionDetector(getActivity());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
        	GetXMLTask task = new GetXMLTask(getActivity());
            SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            boolean kernels_on_start = getPrefs.getBoolean("load_kernels", true);
            if(kernels_on_start == true)
            task.execute(URL);	
        	
        }else{
        	Toast.makeText(getActivity(), R.string.connect_internet, Toast.LENGTH_LONG).show();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
			        long id) {
				vibrator.vibrate(50);
				Object o = listView.getItemAtPosition(position);
				final News obj_itemDetails = (News)o;
			 	HtcAlertDialog.Builder alertDialog = new HtcAlertDialog.Builder(getActivity());
				 
			    alertDialog.setTitle(R.string.install);

			    alertDialog.setMessage(obj_itemDetails.getHeadline() + " - " + obj_itemDetails.getText()
			    		+ "\n" + obj_itemDetails.getTechDetails());

			    alertDialog.setIcon(R.drawable.icon_info);

			 alertDialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int which) {
			        DownloadMapAsync mew = new DownloadMapAsync();
			    	mew.execute(obj_itemDetails.getDescription());
			    }
			  });
			     alertDialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
			         public void onClick(DialogInterface dialog, int which) {
			     
			       dialog.cancel();
			        }
			     });
			      alertDialog.show();
			}
        	
        });
		return view;
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
	        long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(50);  
        if (isInternetPresent) {
        GetXMLTask task = new GetXMLTask(getActivity());
        task.execute(URL);
    }else{
    	Toast.makeText(getActivity(), R.string.connect_internet, Toast.LENGTH_LONG).show();

    }
		
	}
	
	class DownloadMapAsync extends AsyncTask<String, String, String> {
	 	   String result ="";
	 		@Override
	 		protected void onPreExecute() {
	 			super.onPreExecute();
	 			mProgressDialog = new HtcProgressDialog(getActivity());
	 			mProgressDialog.setMessage(getString(R.string.downloading));
	 			mProgressDialog.setProgressStyle(HtcProgressDialog.STYLE_HORIZONTAL);
	 			mProgressDialog.setCancelable(false);
	 			mProgressDialog.show();
	 			
	 		
	 		}

	 		@Override
	 		protected String doInBackground(String... aurl) {
	 			int count;

	 		try {

	 		
	 		URL url = new URL(aurl[0]);
	 		URLConnection conexion = url.openConnection();
	 		conexion.connect();
	 		int lenghtOfFile = conexion.getContentLength();
	 		InputStream input = new BufferedInputStream(url.openStream());
	 		
	 		OutputStream output = new FileOutputStream(zipFile);

	 		byte data[] = new byte[1024];
	 		long total = 0;

	 			while ((count = input.read(data)) != -1) {
	 				total += count;
	 				publishProgress(""+(int)((total*100)/lenghtOfFile));
	 				output.write(data, 0, count);
	 			}
	 			output.close();
	 			input.close();
	 			result = "true";

	 		} catch (Exception e) {
	 			
	 			result = "false";
	 		}
	 		return null;

	 		}
	 		protected void onProgressUpdate(String... progress) {
	 			 Log.d("ANDRO_ASYNC",progress[0]);
	 			 mProgressDialog.setProgress(Integer.parseInt(progress[0]));
	 		}

	 		@Override
	 		protected void onPostExecute(String unused) {
	 			mProgressDialog.dismiss();
	 			if(result.equalsIgnoreCase("true")){
	 	     	Toast.makeText(getActivity(), R.string.download_finish, Toast.LENGTH_LONG).show();

	 			try {
	 				unzip();
	 			} catch (IOException e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}
	 			}
	 			else{
	 				
	 			}
	 		}
	 	}
	public void unzip() throws IOException {
	    mProgressDialog = new HtcProgressDialog(getActivity());
	    mProgressDialog.setMessage(getString(R.string.exctracting));
		mProgressDialog.setProgressStyle(HtcProgressDialog.STYLE_SPINNER);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
	    new UnZipTask().execute(zipFile, unzipLocation);
		 }
	
	private class UnZipTask extends AsyncTask<String, Void, Boolean> {
		@SuppressWarnings("rawtypes")
		@Override
		protected Boolean doInBackground(String... params) {
		String filePath = params[0];
		String destinationPath = params[1];

		File archive = new File(filePath);
		try {
			
			
		   ZipFile zipfile = new ZipFile(archive);
		    for (Enumeration e = zipfile.entries(); e.hasMoreElements();) {
		        ZipEntry entry = (ZipEntry) e.nextElement();
		        unzipEntry(zipfile, entry, destinationPath);
		    }
		    
		 
			    UnzipUtil d = new UnzipUtil(zipFile, unzipLocation); 
		      d.unzip();
		    
		} catch (Exception e) {
		 
		    return false;
		}

		return true;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			  mProgressDialog.dismiss();
			  Intent intent = new Intent(Intent.ACTION_MAIN);
		  	intent.setClassName("org.mikrosmile.my.store", "org.mikrosmile.my.store.FileChooserKernels");
		  	startActivity(intent);

		}


		private void unzipEntry(ZipFile zipfile, ZipEntry entry,
		    String outputDir) throws IOException {

		if (entry.isDirectory()) {
		    createDir(new File(outputDir, entry.getName()));
		    return;
		}

		File outputFile = new File(outputDir, entry.getName());
		if (!outputFile.getParentFile().exists()) {
		    createDir(outputFile.getParentFile());
		}
		BufferedInputStream inputStream = new BufferedInputStream(zipfile.getInputStream(entry));
		BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

		try {

		} finally {
			outputStream.flush();
			outputStream.close();
			inputStream.close();
		   
		    
		}
		}

		private void createDir(File dir) {
		if (dir.exists()) {
		    return;
		}
		if (!dir.mkdirs()) {
		    throw new RuntimeException("Can not create dir " + dir);
		}
		}}
	
	private class GetXMLTask extends AsyncTask<String, Void, List<News>> {
		@Override
 		protected void onPreExecute() {
 			super.onPreExecute();
 			mProgressDialog = new HtcProgressDialog(getActivity());
 			mProgressDialog.setMessage(getString(R.string.loading));
 			mProgressDialog.setProgressStyle(HtcProgressDialog.STYLE_SPINNER);
 			mProgressDialog.setCancelable(false);
 			mProgressDialog.show();
 		}
        private Activity context;
        public GetXMLTask(Activity context) {
            this.context = context;
        }
 
        @Override
        protected void onPostExecute(List<News> newsview) {
            listViewAdapter = new KernelsListView(context, newsview);
            listView.setAdapter(listViewAdapter);
      	  	mProgressDialog.dismiss();
        }
        
 
         private String getXmlFromUrl(String urlString) {
            String xml = null;
 
            try {
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(URL);
 
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                xml = EntityUtils.toString(httpEntity, HTTP.UTF_8);
 
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return xml;
        }  
 
        @Override
        protected List<News> doInBackground(String... urls) {
            List<News> newsview = null;
            String xml = null;
            for (String url : urls) {
                xml = getXmlFromUrl(url);
 
                InputStream stream = new ByteArrayInputStream(xml.getBytes());
                newsview = SAXXMLParser.parse(stream);
 
                for (News newsviewsingle : newsview) {
                    String imageURL = newsviewsingle.getImageURL();
                    Bitmap bitmap = null;
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    bmOptions.inSampleSize = 1;
 
                    try {
                        bitmap = BitmapFactory
                                .decodeStream(new
                                URL(imageURL).openStream(),
                                null, bmOptions);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    newsviewsingle.setImageBitmap(bitmap);
                }
            }
            return newsview;
        }
    }
}