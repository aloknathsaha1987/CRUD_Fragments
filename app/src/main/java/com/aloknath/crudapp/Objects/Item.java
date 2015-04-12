package com.aloknath.crudapp.Objects;

/**
 * Created by ALOKNATH on 4/12/2015.
 */
public class Item {

    private String name;
    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "{\"item\":{" +
                "\"name\":\"" + name + '\"' +
                ",\"category\":\"" + category + "\"}}" ;
    }
}
