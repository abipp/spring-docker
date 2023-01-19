package com.winterhold.dto.category;

import com.winterhold.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryGridDTO {

    private final String id;
    private final Integer floor;
    private final String isle;
    private final Integer bay;
    private final Integer books;

    public CategoryGridDTO(String id, Integer floor, String isle, Integer bay, Integer books) {
        this.id = id;
        this.floor = floor;
        this.isle = isle;
        this.bay = bay;
        this.books = books;
    }

    public static List<CategoryGridDTO> toList(List<Category> categories) {
        List<CategoryGridDTO> result = new ArrayList<>();

        for (Category category: categories) {
            result.add(new CategoryGridDTO(category.getName(),
                    category.getFloor(),
                    category.getIsle(),
                    category.getBay(),
                    category.getBooks().size()
            ));
        }
        return result;
    }

    public String getId() {
        return id;
    }

    public Integer getFloor() {
        return floor;
    }

    public String getIsle() {
        return isle;
    }

    public Integer getBay() {
        return bay;
    }

    public Integer getBooks() {
        return books;
    }
}
