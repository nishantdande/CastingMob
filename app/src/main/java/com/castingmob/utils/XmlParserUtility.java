package com.castingmob.utils;

import android.text.TextUtils;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.regex.Pattern;

/**
 * Created by nishant on 14/03/16.
 */
public class XmlParserUtility {

    public static String parserPTag(String xmlString) throws XmlPullParserException, IOException {
        String pString = null;
        XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
        xmlFactoryObject.setNamespaceAware(true);
        XmlPullParser myparser = xmlFactoryObject.newPullParser();
        myparser.setInput(new StringReader(xmlString));

        int eventType = myparser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if(eventType == XmlPullParser.TEXT) {
                pString = myparser.getText();
            }
            eventType = myparser.next();
        }

        return pString == null ? xmlString : pString;
    }

    public static String parserFWDTag(String xmlString) throws XmlPullParserException, IOException {
        String fwdText= Pattern.compile("(<a>)([\\s\\S]*?)(</a>)").matcher(xmlString).replaceAll("$1$3");

        fwdText = fwdText.replaceAll("<.*?>", "");

       return fwdText == null ? null : fwdText;
    }

    public static String[] parserTag(String xmlString) throws XmlPullParserException, IOException {
        String pString[] = new String[2];
        StringBuilder stringBuilder = new StringBuilder();
        XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
        xmlFactoryObject.setNamespaceAware(true);
        XmlPullParser myparser = xmlFactoryObject.newPullParser();
        myparser.setInput(new StringReader(xmlString));

        int eventType = myparser.getEventType();
        System.out.println("-----------------------------------");
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_DOCUMENT) {
                System.out.println("Start document");
            } else if (eventType == XmlPullParser.END_DOCUMENT) {
                System.out.println("End document");
            } else if (eventType == XmlPullParser.START_TAG) {
                System.out.println("Start tag " + myparser.getName());
                System.out.println("Start tag " + myparser.getAttributeValue(null, "src"));
                if (TextUtils.isEmpty(pString[0])&& myparser.getAttributeValue(null,"src") !=null){
                    pString[0] = myparser.getAttributeValue(null,"src");
                    Log.d("imageFeed", pString[0] + " "+myparser.getAttributeValue(null, "src"));
                }
            } else if (eventType == XmlPullParser.END_TAG) {
                System.out.println("End tag " + myparser.getName());
            } else if (eventType == XmlPullParser.TEXT) {
                System.out.println("Text " + myparser.getText());
                stringBuilder.append(myparser.getText()+" ");
            }
            try
            {
                eventType = myparser.next();
            }catch (Exception e){
                continue;
            }
        }
        System.out.println("-----------------------------------");

        pString[1] = stringBuilder.toString();
        return pString;
    }

}
