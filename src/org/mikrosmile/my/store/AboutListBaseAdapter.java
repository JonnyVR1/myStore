package org.mikrosmile.my.store;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
 
public class AboutListBaseAdapter extends BaseAdapter {
 private static ArrayList<AboutDetails> aboutDetailsrrayList;
    
 private LayoutInflater l_Inflater;
 
 public AboutListBaseAdapter(Context context, ArrayList<AboutDetails> results) {
	 aboutDetailsrrayList = results;
  l_Inflater = LayoutInflater.from(context);
 }
 
 public int getCount() {
  return aboutDetailsrrayList.size();
 }
 
 public Object getItem(int position) {
  return aboutDetailsrrayList.get(position);
 }
 
 public long getItemId(int position) {
  return position;
 }
 
 public View getView(int position, View convertView, ViewGroup parent) {
  ViewHolder holder;
  if (convertView == null) {
   convertView = l_Inflater.inflate(R.layout.about_details, null);
   holder = new ViewHolder();
   holder.txt_itemName = (TextView) convertView.findViewById(R.id.name);
   holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.itemDescription);
 
   convertView.setTag(holder);
  } else {
   holder = (ViewHolder) convertView.getTag();
  }
   
  holder.txt_itemName.setText(aboutDetailsrrayList.get(position).getName());
  holder.txt_itemDescription.setText(aboutDetailsrrayList.get(position).getItemDescription());
 
  return convertView;
 }
 
 static class ViewHolder {
  TextView txt_itemName;
  TextView txt_itemDescription;
 }
}

