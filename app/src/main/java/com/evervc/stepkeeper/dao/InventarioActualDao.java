package com.evervc.stepkeeper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.evervc.stepkeeper.models.InventarioActual;

@Dao
public interface InventarioActualDao {

    @Insert
    Long insertarInventario(InventarioActual inventarioActual);

    @Query("SELECT * FROM inventario_actual WHERE id_producto = :idProducto")
    InventarioActual obtenerInventarioPorProducto(int idProducto);

    @Update
    int actualizarInventario(InventarioActual inventarioActual);

    @Delete
    int eliminarInventario(InventarioActual inventarioActual);
}

