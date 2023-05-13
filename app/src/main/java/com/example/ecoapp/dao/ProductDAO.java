package com.example.ecoapp.dao;

import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ecoapp.entity.Product;

import java.util.List;

@Dao
public interface ProductDAO {

    @Query("SELECT * FROM Product")
    public List<Product> getAllProduct();

    @Query("SELECT * FROM Product WHERE name = :name")
    public List<Product> getProductByName(String name);

    @Insert(onConflict = REPLACE)
    public void insert(Product product);

    @Update
    public void update(Product product);

    @Delete
    public void delete(Product product);
}
