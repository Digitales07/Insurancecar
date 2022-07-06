package com.adiljutt.inscurancecar.model;

/**
 * Created by Adil Jutt on 7/5/2019.
 */

public class PatsAddedData {
    private String conditions,currentuser,deps,messageKey_id,part_name,prices,totals;

    public PatsAddedData(String conditions, String currentuser, String deps, String messageKey_id, String part_name, String prices, String totals) {
        this.conditions = conditions;
        this.currentuser = currentuser;
        this.deps = deps;
        this.messageKey_id = messageKey_id;
        this.part_name = part_name;
        this.prices = prices;
        this.totals = totals;
    }

    public PatsAddedData() {
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getCurrentuser() {
        return currentuser;
    }

    public void setCurrentuser(String currentuser) {
        this.currentuser = currentuser;
    }

    public String getDeps() {
        return deps;
    }

    public void setDeps(String deps) {
        this.deps = deps;
    }

    public String getMessageKey_id() {
        return messageKey_id;
    }

    public void setMessageKey_id(String messageKey_id) {
        this.messageKey_id = messageKey_id;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    public String getTotals() {
        return totals;
    }

    public void setTotals(String totals) {
        this.totals = totals;
    }
}
