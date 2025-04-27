package com.evervc.stepkeeper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.evervc.stepkeeper.models.Producto;
import com.evervc.stepkeeper.pojo.ProductoPojo;

import java.util.List;

@Dao
public interface ProductoDao {

    @Insert
    Long insertarProducto(Producto producto);

    @Update
    int actualizarProducto(Producto producto);

    @Delete
    int eliminarProducto(Producto producto);

    @Query("SELECT * FROM productos")
    List<Producto> obtenerTodosLosProductos();

    @Query("SELECT * FROM productos WHERE id = :id")
    Producto obtenerProductoPorId(int id);

    // Obtener Producto con su Categoria
    @Transaction
    @Query("SELECT * FROM productos")
    List<ProductoPojo> obtenerProductosConCategoria();
}


