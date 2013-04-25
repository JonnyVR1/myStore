package org.mikrosmile.my.store;

import java.util.List;
import org.mikrosmile.my.store.News;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class SystemModsListView extends ArrayAdapter<News> {
    Activity context;
    List<News> news;
 
    public SystemModsListView(Activity context, List<News> news) {
        super(context, R.layout.mods_details, news);
        this.context = context;
        this.news = news;
    }
 
    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtText;     
        TextView txtHeadline;

    }
 
    public News getItem(int position) {
        return news.get(position);
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();
 
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mods_details, null);
            holder = new ViewHolder();
            holder.txtHeadline = (TextView) convertView.findViewById(R.id.headline);
            holder.txtText = (TextView) convertView.findViewById(R.id.text); 
            holder.imageView = (ImageView) convertView.findViewById(R.id.thumbnail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        News newsitem = (News) getItem(position);
 
        holder.txtHeadline.setText(newsitem.getHeadline());
        holder.txtText.setText(newsitem.getText());
        holder.imageView.setImageBitmap(newsitem.getImageBitmap());
 
        return convertView;
    }
}