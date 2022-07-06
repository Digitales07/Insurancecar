package com.adiljutt.inscurancecar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Adil Jutt on 6/30/2019.
 */

public class Parts implements Serializable {

    @SerializedName("part_id")
    @Expose
    private String part_id;
    @SerializedName("part_name")
    @Expose
    private String part_name;

    public String getPart_id() {
        return part_id;
    }

    public void setPart_id(String part_id) {
        this.part_id = part_id;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }
}
