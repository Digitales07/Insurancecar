package com.adiljutt.inscurancecar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Adil Jutt on 6/27/2019.
 */

public class Cases implements Serializable {

    @SerializedName("messageKey_id")
    @Expose
    private String messageKey_id;
    @SerializedName("auto_cuurent_dates")
    @Expose
    private String auto_cuurent_dates;
    @SerializedName("owner_names")
    @Expose
    private String owner_names;
    @SerializedName("father_names")
    @Expose
    private String father_names;
    @SerializedName("cnics")
    @Expose
    private String cnics;
    @SerializedName("workshop_names")
    @Expose
    private String workshop_names;
    @SerializedName("car_numbers")
    @Expose
    private String car_numbers;

    @SerializedName("asking_amounts")
    @Expose
    private String asking_amounts;

    @SerializedName("settle_amounts")
    @Expose
    private String settle_amounts;

    @SerializedName("taxs")
    @Expose
    private String taxs;

    @SerializedName("insurance_history_amounts")
    @Expose
    private String insurance_history_amounts;

    @SerializedName("custom_dates")
    @Expose
    private String custom_dates;

    @SerializedName("insurance_ids")
    @Expose
    private String insurance_ids;

    @SerializedName("city_name")
    @Expose
    private String city_name;

    @SerializedName("engine_numbers")
    @Expose
    private String engine_numbers;

    @SerializedName("chasis_numbers")
    @Expose
    private String chasis_numbers;

    @SerializedName("sum_insureds")
    @Expose
    private String sum_insureds;

    @SerializedName("car_companys")
    @Expose
    private String car_companys;

    @SerializedName("premiums")
    @Expose
    private String premiums;
    @SerializedName("models")
    @Expose
    private String models;

    @SerializedName("branchs")
    @Expose
    private String branchs;
    @SerializedName("detuctables")
    @Expose
    private String detuctables;

    @SerializedName("reinspections")
    @Expose
    private String reinspections;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("previous_amount_details")
    @Expose
    private String previous_amount_details;
    @SerializedName("previous_losses__lodgement_date")
    @Expose
    private String previous_losses__lodgement_date;
    @SerializedName("custom_lose_dates")
    @Expose
    private String custom_lose_dates;

    public String getMessageKey_id() {
        return messageKey_id;
    }

    public void setMessageKey_id(String messageKey_id) {
        this.messageKey_id = messageKey_id;
    }

    public String getAuto_cuurent_dates() {
        return auto_cuurent_dates;
    }

    public void setAuto_cuurent_dates(String auto_cuurent_dates) {
        this.auto_cuurent_dates = auto_cuurent_dates;
    }

    public String getOwner_names() {
        return owner_names;
    }

    public void setOwner_names(String owner_names) {
        this.owner_names = owner_names;
    }

    public String getFather_names() {
        return father_names;
    }

    public void setFather_names(String father_names) {
        this.father_names = father_names;
    }

    public String getCnics() {
        return cnics;
    }

    public void setCnics(String cnics) {
        this.cnics = cnics;
    }

    public String getWorkshop_names() {
        return workshop_names;
    }

    public void setWorkshop_names(String workshop_names) {
        this.workshop_names = workshop_names;
    }

    public String getCar_numbers() {
        return car_numbers;
    }

    public void setCar_numbers(String car_numbers) {
        this.car_numbers = car_numbers;
    }

    public String getAsking_amounts() {
        return asking_amounts;
    }

    public void setAsking_amounts(String asking_amounts) {
        this.asking_amounts = asking_amounts;
    }

    public String getSettle_amounts() {
        return settle_amounts;
    }

    public void setSettle_amounts(String settle_amounts) {
        this.settle_amounts = settle_amounts;
    }

    public String getTaxs() {
        return taxs;
    }

    public void setTaxs(String taxs) {
        this.taxs = taxs;
    }

    public String getInsurance_history_amounts() {
        return insurance_history_amounts;
    }

    public void setInsurance_history_amounts(String insurance_history_amounts) {
        this.insurance_history_amounts = insurance_history_amounts;
    }

    public String getCustom_dates() {
        return custom_dates;
    }

    public void setCustom_dates(String custom_dates) {
        this.custom_dates = custom_dates;
    }

    public String getInsurance_ids() {
        return insurance_ids;
    }

    public void setInsurance_ids(String insurance_ids) {
        this.insurance_ids = insurance_ids;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getEngine_numbers() {
        return engine_numbers;
    }

    public void setEngine_numbers(String engine_numbers) {
        this.engine_numbers = engine_numbers;
    }

    public String getChasis_numbers() {
        return chasis_numbers;
    }

    public void setChasis_numbers(String chasis_numbers) {
        this.chasis_numbers = chasis_numbers;
    }

    public String getSum_insureds() {
        return sum_insureds;
    }

    public void setSum_insureds(String sum_insureds) {
        this.sum_insureds = sum_insureds;
    }

    public String getCar_companys() {
        return car_companys;
    }

    public void setCar_companys(String car_companys) {
        this.car_companys = car_companys;
    }

    public String getPremiums() {
        return premiums;
    }

    public void setPremiums(String premiums) {
        this.premiums = premiums;
    }

    public String getModels() {
        return models;
    }

    public void setModels(String models) {
        this.models = models;
    }

    public String getBranchs() {
        return branchs;
    }

    public void setBranchs(String branchs) {
        this.branchs = branchs;
    }

    public String getDetuctables() {
        return detuctables;
    }

    public void setDetuctables(String detuctables) {
        this.detuctables = detuctables;
    }

    public String getReinspections() {
        return reinspections;
    }

    public void setReinspections(String reinspections) {
        this.reinspections = reinspections;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPrevious_amount_details() {
        return previous_amount_details;
    }

    public void setPrevious_amount_details(String previous_amount_details) {
        this.previous_amount_details = previous_amount_details;
    }

    public String getPrevious_losses__lodgement_date() {
        return previous_losses__lodgement_date;
    }

    public void setPrevious_losses__lodgement_date(String previous_losses__lodgement_date) {
        this.previous_losses__lodgement_date = previous_losses__lodgement_date;
    }

    public String getCustom_lose_dates() {
        return custom_lose_dates;
    }

    public void setCustom_lose_dates(String custom_lose_dates) {
        this.custom_lose_dates = custom_lose_dates;
    }
}
