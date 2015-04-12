package com.aloknath.crudapp.Objects;

/**
 * Created by ALOKNATH on 4/12/2015.
 */
public class ItemObject {
    private String category;
    private String created_at;
    private int id;
    private String name;
    private String updated_at;
    private int user_id;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ItemObject{" +
                "category='" + category + '\'' +
                ", created_at='" + created_at + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
