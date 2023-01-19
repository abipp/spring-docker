package com.winterhold.dto.category;

public class UpsertCategoryDTO {

    private  String id;
    private  Integer floor;
    private  String isle;
    private  Integer bay;

    public UpsertCategoryDTO() {
    }

    public UpsertCategoryDTO(String id, Integer floor, String isle, Integer bay) {
        this.id = id;
        this.floor = floor;
        this.isle = isle;
        this.bay = bay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getIsle() {
        return isle;
    }

    public void setIsle(String isle) {
        this.isle = isle;
    }

    public Integer getBay() {
        return bay;
    }

    public void setBay(Integer bay) {
        this.bay = bay;
    }
}
