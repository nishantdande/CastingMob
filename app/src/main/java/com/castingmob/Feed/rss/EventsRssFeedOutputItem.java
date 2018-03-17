package com.castingmob.Feed.rss;

import java.net.URI;
import java.util.Date;

/**
 * Created by jayesh on 28/1/16.
 */
public class EventsRssFeedOutputItem {


    String familyEventCategory;
    String familyEventOverview;
    String familyEventOverviewTitle;
    String familyEventSubtitle;
    String familyEventTitle;
    String familyEventType;
    String sessionContent;
    String sessionContentHeading;
    String sessionCountry;
    Date sessionEnd;
    String sessionGuid;
    String sessionLanguage;
    String sessionLastUpdated;
    URI sessionLink;
    String sessionLocation;
    String sessionLocationLine1;
    String sessionLocationLine2;
    String sessionLocationLine3;
    String sessionLocationVenueLink;
    String sessionPrice;
    URI sessionRegisterLink;
    Date sessionStart;
    String sessionStartTime;

    public void setFamilyEventCategory(String familyEventCategory) {
        this.familyEventCategory = familyEventCategory;
    }

    public void setFamilyEventOverview(String familyEventOverview) {
        this.familyEventOverview = familyEventOverview;
    }

    public void setFamilyEventOverviewTitle(String familyEventOverviewTitle) {
        this.familyEventOverviewTitle = familyEventOverviewTitle;
    }

    public void setFamilyEventSubtitle(String familyEventSubtitle) {
        this.familyEventSubtitle = familyEventSubtitle;
    }

    public void setFamilyEventTitle(String familyEventTitle) {
        this.familyEventTitle = familyEventTitle;
    }

    public void setFamilyEventType(String familyEventType) {
        this.familyEventType = familyEventType;
    }

    public void setSessionContent(String sessionContent) {
        this.sessionContent = sessionContent;
    }

    public void setSessionContentHeading(String sessionContentHeading) {
        this.sessionContentHeading = sessionContentHeading;
    }

    public void setSessionCountry(String sessionCountry) {
        this.sessionCountry = sessionCountry;
    }

    public void setSessionEnd(Date sessionEnd) {
        this.sessionEnd = sessionEnd;
    }

    public void setSessionGuid(String sessionGuid) {
        this.sessionGuid = sessionGuid;
    }

    public void setSessionLanguage(String sessionLanguage) {
        this.sessionLanguage = sessionLanguage;
    }

    public void setSessionLastUpdated(String sessionLastUpdated) {
        this.sessionLastUpdated = sessionLastUpdated;
    }

    public void setSessionLink(URI sessionLink) {
        this.sessionLink = sessionLink;
    }

    public void setSessionLocation(String sessionLocation) {
        this.sessionLocation = sessionLocation;
    }

    public void setSessionLocationLine1(String sessionLocationLine1) {
        this.sessionLocationLine1 = sessionLocationLine1;
    }

    public void setSessionLocationLine2(String sessionLocationLine2) {
        this.sessionLocationLine2 = sessionLocationLine2;
    }

    public void setSessionLocationLine3(String sessionLocationLine3) {
        this.sessionLocationLine3 = sessionLocationLine3;
    }

    public void setSessionLocationVenueLink(String sessionLocationVenueLink) {
        this.sessionLocationVenueLink = sessionLocationVenueLink;
    }

    public void setSessionPrice(String sessionPrice) {
        this.sessionPrice = sessionPrice;
    }

    public void setSessionRegisterLink(URI sessionRegisterLink) {
        this.sessionRegisterLink = sessionRegisterLink;
    }

    public void setSessionStart(Date sessionStart) {
        this.sessionStart = sessionStart;
    }

    public void setSessionStartTime(String sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }

    @Override
    public String toString() {
        return "EventsRssFeedOutputItem{" +
                "familyEventCategory='" + familyEventCategory + '\'' +
                ", familyEventOverview='" + familyEventOverview + '\'' +
                ", familyEventOverviewTitle='" + familyEventOverviewTitle + '\'' +
                ", familyEventSubtitle='" + familyEventSubtitle + '\'' +
                ", familyEventTitle='" + familyEventTitle + '\'' +
                ", familyEventType='" + familyEventType + '\'' +
                ", sessionContent='" + sessionContent + '\'' +
                ", sessionContentHeading='" + sessionContentHeading + '\'' +
                ", sessionCountry='" + sessionCountry + '\'' +
                ", sessionEnd=" + sessionEnd +
                ", sessionGuid='" + sessionGuid + '\'' +
                ", sessionLanguage='" + sessionLanguage + '\'' +
                ", sessionLastUpdated='" + sessionLastUpdated + '\'' +
                ", sessionLink=" + sessionLink +
                ", sessionLocation='" + sessionLocation + '\'' +
                ", sessionLocationLine1='" + sessionLocationLine1 + '\'' +
                ", sessionLocationLine2='" + sessionLocationLine2 + '\'' +
                ", sessionLocationLine3='" + sessionLocationLine3 + '\'' +
                ", sessionLocationVenueLink='" + sessionLocationVenueLink + '\'' +
                ", sessionPrice='" + sessionPrice + '\'' +
                ", sessionRegisterLink=" + sessionRegisterLink +
                ", sessionStart=" + sessionStart +
                ", sessionStartTime='" + sessionStartTime + '\'' +
                '}';
    }
}