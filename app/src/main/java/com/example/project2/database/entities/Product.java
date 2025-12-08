package com.example.project2.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.example.project2.database.ProduceLogDatabase;

@Entity(tableName = ProduceLogDatabase.PRODUCT_TABLE)
public class Product {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String type;

    @NonNull
    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                '}';
    }

    public Product(String name, String type) {
        this.name = name;
        if(!ProduceLogDatabase.TYPES.contains(type.toLowerCase())) {
            type = "seasoning";
        }
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(name, product.name) && Objects.equals(type, product.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if(!ProduceLogDatabase.TYPES.contains(type.toLowerCase())) {
            type = "seasoning";
        }
        this.type = type;
    }
}
