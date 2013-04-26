package org.mikrosmile.my.store;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.htc.app.HtcProgressDialog;
import com.htc.widget.ActionBarItemView;
import com.htc.widget.ActionBarText;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class NewsFragment extends Fragment implements
OnClickListener, OnItemClickListener {
	Boolean isInternetPresent = false;
    ConnectionDetector cd;
	private HtcProgressDialog mProgressDialog;
    public static ActionBarItemView mActionButton;
    public static ActionBarText mActionText;
    Button button;
    ListView listView;
    List<News> newsview;
    NewsListViewAdapter listViewAdapter;
    String URL = "";
    
    public NewsFragment() {      
    }
    
    @SuppressWarnings("static-access")
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
                             Bundle savedInstanceState) { 
    	View view =  inflater.inflate(R.layout.news_activity, container, false);
    	URL = cd.getUrls("ro.news");
    	button = (Button) view.findViewById(R.id.button);
        listView = (ListView) view.findViewById(R.id.newsList);
        button.setOnClickListener(this);
        cd = new ConnectionDetector(getActivity());
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) {
        	GetXMLTask task = new GetXMLTask(getActivity());
            SharedPreferences getPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            boolean news_on_start = getPrefs.getBoolean("load_news", true);
            if(news_on_start == true)
            task.execute(URL);	
        	
        }else{
        	Toast.makeText(getActivity(), R.string.connect_internet, Toast.LENGTH_LONG).show();
        }

    	return view;
    	
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
        long id) {

    }
    
 
    @Override
    public void onClick(View view) {
    	Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(50);  
        if (isInternetPresent) {
        GetXMLTask task = new GetXMLTask(getActivity());
        task.execute(URL);
       
    }else{
    	Toast.makeText(getActivity(), R.string.connect_internet, Toast.LENGTH_LONG).show();

    }
    }
    
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
            listViewAdapter = new NewsListViewAdapter(context, newsview);
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
