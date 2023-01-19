package com.winterhold.entity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "Category")
public class Category {

    @Id
    @Column(name = "Name")
    private String name;

    @Column(name = "Floor")
    private Integer floor;

    @Column(name = "Isle")
    private String isle;

    @Column(name = "Bay")
    private Integer bay;

    @OneToMany(mappedBy = "categoryName")
    private Set<Book> books = new LinkedHashSet<>();

    public Category() {
    }

    public Category(String name, Integer floor, String isle, Integer bay) {
        this.name = name;
        this.floor = floor;
        this.isle = isle;
        this.bay = bay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
