package com.adiljutt.inscurancecar.model;

/**
 * Created by Adil Jutt on 7/10/2019.
 */

public class VoiceReports {

    private String report_id,ntns,addresss,particularss,policys,losss,pofessional_descriptions,pofessional_fees,reinspection_descriptions,
            reinspection_fees,conveyance_descriptions,local_conveyance_fees,photographss,photographs_fees,grandtotals;


    public VoiceReports(String report_id, String ntns, String addresss, String particularss, String policys, String losss, String pofessional_descriptions, String pofessional_fees, String reinspection_descriptions, String reinspection_fees, String conveyance_descriptions, String local_conveyance_fees, String photographss, String photographs_fees, String grandtotals) {
        this.report_id = report_id;
        this.ntns = ntns;
        this.addresss = addresss;
        this.particularss = particularss;
        this.policys = policys;
        this.losss = losss;
        this.pofessional_descriptions = pofessional_descriptions;
        this.pofessional_fees = pofessional_fees;
        this.reinspection_descriptions = reinspection_descriptions;
        this.reinspection_fees = reinspection_fees;
        this.conveyance_descriptions = conveyance_descriptions;
        this.local_conveyance_fees = local_conveyance_fees;
        this.photographss = photographss;
        this.photographs_fees = photographs_fees;
        this.grandtotals = grandtotals;
    }

    public VoiceReports() {
    }

    public String getReport_id() {
        return report_id;
    }

    public void setReport_id(String report_id) {
        this.report_id = report_id;
    }

    public String getNtns() {
        return ntns;
    }

    public void setNtns(String ntns) {
        this.ntns = ntns;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    public String getParticularss() {
        return particularss;
    }

    public void setParticularss(String particularss) {
        this.particularss = particularss;
    }

    public String getPolicys() {
        return policys;
    }

    public void setPolicys(String policys) {
        this.policys = policys;
    }

    public String getLosss() {
        return losss;
    }

    public void setLosss(String losss) {
        this.losss = losss;
    }

    public String getPofessional_descriptions() {
        return pofessional_descriptions;
    }

    public void setPofessional_descriptions(String pofessional_descriptions) {
        this.pofessional_descriptions = pofessional_descriptions;
    }

    public String getPofessional_fees() {
        return pofessional_fees;
    }

    public void setPofessional_fees(String pofessional_fees) {
        this.pofessional_fees = pofessional_fees;
    }

    public String getReinspection_descriptions() {
        return reinspection_descriptions;
    }

    public void setReinspection_descriptions(String reinspection_descriptions) {
        this.reinspection_descriptions = reinspection_descriptions;
    }

    public String getReinspection_fees() {
        return reinspection_fees;
    }

    public void setReinspection_fees(String reinspection_fees) {
        this.reinspection_fees = reinspection_fees;
    }

    public String getConveyance_descriptions() {
        return conveyance_descriptions;
    }

    public void setConveyance_descriptions(String conveyance_descriptions) {
        this.conveyance_descriptions = conveyance_descriptions;
    }

    public String getLocal_conveyance_fees() {
        return local_conveyance_fees;
    }

    public void setLocal_conveyance_fees(String local_conveyance_fees) {
        this.local_conveyance_fees = local_conveyance_fees;
    }

    public String getPhotographss() {
        return photographss;
    }

    public void setPhotographss(String photographss) {
        this.photographss = photographss;
    }

    public String getPhotographs_fees() {
        return photographs_fees;
    }

    public void setPhotographs_fees(String photographs_fees) {
        this.photographs_fees = photographs_fees;
    }

    public String getGrandtotals() {
        return grandtotals;
    }

    public void setGrandtotals(String grandtotals) {
        this.grandtotals = grandtotals;
    }
}
