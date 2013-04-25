package org.mikrosmile.my.store;

import java.io.InputStream;
import java.util.List;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.mikrosmile.my.store.News;
import android.util.Log;
 
public class SAXXMLParser {
	 
    public static List<News> parse(InputStream is) {
        List<News> news = null;
        try {
            XMLReader xmlReader = SAXParserFactory.newInstance().newSAXParser()
                    .getXMLReader();
            SAXXMLHandler saxHandler = new SAXXMLHandler();
            xmlReader.setContentHandler(saxHandler);
            xmlReader.parse(new InputSource(is));
            news = saxHandler.getNews();
 
        } catch (Exception ex) {
            Log.d("XML", "SAXXMLParser: parse() failed");
            ex.printStackTrace();
        }
 
        return news;
    }
}

