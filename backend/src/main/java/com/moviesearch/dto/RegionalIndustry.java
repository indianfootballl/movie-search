package com.moviesearch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegionalIndustry {
    private String code;
    private String name;
    private String flag;
    private String description;

    public RegionalIndustry() {}

    public RegionalIndustry(String code, String name, String flag, String description) {
        this.code = code;
        this.name = name;
        this.flag = flag;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
