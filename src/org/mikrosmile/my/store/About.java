package org.mikrosmile.my.store;


import java.util.ArrayList;

import com.htc.widget.HtcAlertDialog;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class About extends Fragment {
	ListView lv1;
	
	public About() {      
    }
	
	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
                             Bundle savedInstanceState) { 
		View view =  inflater.inflate(R.layout.about_activity, container, false);
		ArrayList<AboutDetails> image_details = GetSearchResults();
		final ListView lv1 = (ListView) view.findViewById(R.id.about_list);
		lv1.setAdapter(new AboutListBaseAdapter(getActivity(), image_details));
		lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
			@Override
			public void onItemClick(AdapterView<?> a, View v, int position, long id) {
			    vibrator.vibrate(50); 
			Object o = lv1.getItemAtPosition(position);
			final AboutDetails obj_itemDetails = (AboutDetails)o;
			if(position == 0){
				HtcAlertDialog.Builder builder2 = new HtcAlertDialog.Builder(getActivity());
				builder2.setIcon(R.drawable.icon_info)
						.setTitle(obj_itemDetails.getName())
						.setItems(R.array.mikrosmile,
			new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			@SuppressWarnings("unused")
			String[] items = getResources().getStringArray(R.array.mikrosmile);

			if (id == 0){
				   startActivity(new Intent("android.intent.action.VIEW", Uri.parse(obj_itemDetails.getUrl())));
			}else if(id == 1){
				   startActivity(new Intent("android.intent.action.VIEW", Uri.parse(obj_itemDetails.getPayPal())));
			}else if(id == 2){
				startActivity(new Intent("android.intent.action.VIEW", Uri.parse(obj_itemDetails.getTwitter())));
				}else if(id ==3){
					startActivity(new Intent("android.intent.action.VIEW", Uri.parse(obj_itemDetails.getMyStore())));
				}else if(id ==4){
					startActivity(new Intent("android.intent.action.VIEW", Uri.parse(obj_itemDetails.getDs())));
				}
					}
						}).create();
					builder2.show();
				
			}else{
				HtcAlertDialog.Builder builder2 = new HtcAlertDialog.Builder(getActivity());
				builder2.setIcon(R.drawable.icon_info)
						.setTitle(obj_itemDetails.getName())
						.setItems(R.array.select_dialog_items,
			new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			@SuppressWarnings("unused")
			String[] items = getResources().getStringArray(R.array.select_dialog_items);

			if (id == 0){
				   startActivity(new Intent("android.intent.action.VIEW", Uri.parse(obj_itemDetails.getUrl())));
			}else if(id == 1){
				   startActivity(new Intent("android.intent.action.VIEW", Uri.parse(obj_itemDetails.getPayPal())));
			}else if(id == 2){
				startActivity(new Intent("android.intent.action.VIEW", Uri.parse(obj_itemDetails.getTwitter())));
				}
					}
						}).create();
					builder2.show();
				
			}
				

				} 
			});
		
		
		
		
		
		return view;
	}

	private ArrayList<AboutDetails> GetSearchResults(){
		ArrayList<AboutDetails> results = new ArrayList<AboutDetails>();
		 
		AboutDetails item_details = new AboutDetails();
		item_details.setName(R.string.mikrosmile);
		item_details.setItemDescription(R.string.mikrosmile_info);
		item_details.setImageNumber(1);
		item_details.setUrl("http://forum.xda-developers.com/member.php?u=4382376");
		item_details.setPayPal("http://forum.xda-developers.com/donatetome.php?u=4382376");
		item_details.setTwitter("http://twitter.com/mikrosmile");
		item_details.setMyStore("http://forum.xda-developers.com/showthread.php?t=2230067");
		item_details.setDs("http://forum.xda-developers.com/showthread.php?t=1999915");
		results.add(item_details); 

		item_details = new AboutDetails();
		item_details.setName(R.string.jonny);
		item_details.setItemDescription(R.string.jonny_info);
		item_details.setImageNumber(2);
		item_details.setUrl("http://forum.xda-developers.com/member.php?u=4184142");
		item_details.setPayPal("http://forum.xda-developers.com/donatetome.php?u=4184142");
		item_details.setTwitter("http://twitter.com/JonnyXDA");
		results.add(item_details);

		item_details = new AboutDetails();
		item_details.setName(R.string.rom_author);
		item_details.setItemDescription(R.string.rom_author_summary);
		item_details.setImageNumber(3);
		item_details.setUrl("http://forum.xda-developers.com/member.php?u=YOURID");
		item_details.setPayPal("http://forum.xda-developers.com/donatetome.php?u=YOURID");
		item_details.setTwitter("http://twitter.com/YOURTWITNAME");
		results.add(item_details);
		 
		return results;
			}

}
