package com.adiljutt.inscurancecar.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Adil Jutt on 6/27/2019.
 */

public class Cities implements Serializable {

    @SerializedName("ids")
    @Expose
    private Integer ids;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
