package com.example.fragments.Model.List;

public class ListResultModel {
    String description;
    int favourite_count;
    String id;
    int item_count;
    String list_type;
    String name;

    public ListResultModel(String description, int favourite_count, String id, int item_count, String list_type, String name) {
        this.description = description;
        this.favourite_count = favourite_count;
        this.id = id;
        this.item_count = item_count;
        this.list_type = list_type;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public int getFavourite_count() {
        return favourite_count;
    }

    public String getId() {
        return id;
    }

    public int getItem_count() {
        return item_count;
    }

    public String getList_type() {
        return list_type;
    }

    public String getName() {
        return name;
    }
}
