package com.castingmob.account.model;
/*
 ==============================================
 Author      : nishant dande
 Version     : 
 Copyright   :
 Description : Account Class Model
 Date        : 14/01/16
 ==============================================
 */

public class Account {

    public enum Type {
        model(0),
        agent(1),
        client(2);

        int id;
        Type(int id) {
            this.id = id;
        }

        public int getValue() {
            return id;
        }
    }

    public enum subType {
        photographer(0),
        designer(1),
        makeupartist(2),
        stylist(3),
        model(4),
        nothing(-1);

        int id;
        subType(int id) {
            this.id = id;
        }

        public int getValue() {
            return id;
        }
    }

    private String email;
    private String website;
    private Type type;
    private subType subType;

    /**
     * Model Account object
     * @param email
     * @param type
     */
    public Account(String email, Type type) {
        this.email = email;
        this.type = type;
        this.website = "";
    }

    /**
     * Agent Account object
     * @param email
     * @param type
     * @param website
     */
    public Account(String email, Type type, String website) {
        this.email = email;
        this.type = type;
        this.website = website;
    }

    /**
     * Client Account object
     * @param email
     * @param type
     * @param subType
     * @param website
     */
    public Account(String email, Type type, subType subType, String website) {
        this.email = email;
        this.type = type;
        this.subType = subType;
        this.website = website;
    }

    public String getEmail() {
        return email;
    }


    public String getWebsite() {
        return website;
    }

    public Type getType() {
        return type;
    }


    public Account.subType getSubType() {
        return subType;
    }

}
