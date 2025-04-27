package com.evervc.stepkeeper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.evervc.stepkeeper.models.Categoria;

import java.util.List;

@Dao
public interface CategoriaDao {

    @Insert
    Long insertarCategoria(Categoria categoria);

    @Query("SELECT * FROM categorias")
    List<Categoria> obtenerTodasLasCategorias();

    @Query("SELECT * FROM categorias WHERE id = :id")
    Categoria obtenerCategoriaPorId(int id);

    @Update
    int actualizarCategoria(Categoria categoria);

    @Delete
    int eliminarCategoria(Categoria categoria);
}

