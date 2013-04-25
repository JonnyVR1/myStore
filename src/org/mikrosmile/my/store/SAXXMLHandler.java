package org.mikrosmile.my.store;

import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import org.mikrosmile.my.store.News;;
 
public class SAXXMLHandler extends DefaultHandler {
    private List<News> news;
    private String tempVal;
    private News newsitem;
 
    public SAXXMLHandler() {
        news = new ArrayList<News>();
    }
 
    public List<News> getNews() {
        return news;
    }
 

    public void startElement(String uri, String localName, String qName,
            Attributes attributes) throws SAXException {

        tempVal = "";
        if (qName.equalsIgnoreCase("newsitem")) {
            newsitem = new News();
            newsitem.setHeadline(attributes.getValue("headline"));
        }
    }
 
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        tempVal = new String(ch, start, length);
    }
 
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
 
        if (qName.equalsIgnoreCase("newsitem")) {
            news.add(newsitem);
        } else if (qName.equalsIgnoreCase("text")) {
            newsitem.setText(tempVal);
        } else if (qName.equalsIgnoreCase("description")) {
            newsitem.setDescription(tempVal);
        } else if (qName.equalsIgnoreCase("technical-details")) {
            newsitem.setTechDetails(tempVal);
        } else if (qName.equalsIgnoreCase("image-url")) {
            newsitem.setImageURL(tempVal);
        } else if (qName.equalsIgnoreCase("date")) {
            newsitem.setDate(tempVal);
        }
    }
}