package com.castingmob.Feed.rss;

/**
 * Created by nishant on 08/03/16.
 */
public class ReadRss {

    private final static String Event_News = "http://models.com/mdcdb/rss/output.xml";

    public static void main(String[] args) {
        RSSReader rssReader = new RSSReader();
        try {
            RSSFeed feed = rssReader.load(Event_News);
            System.out.print(feed.getItems());
        } catch (RSSReaderException e) {
            e.printStackTrace();
        }
    }
}
