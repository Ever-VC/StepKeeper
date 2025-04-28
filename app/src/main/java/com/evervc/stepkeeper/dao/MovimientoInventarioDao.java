package com.evervc.stepkeeper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.evervc.stepkeeper.models.MovimientoInventario;
import com.evervc.stepkeeper.pojo.MovimientoInventarioPojo;

import java.util.List;

@Dao
public interface MovimientoInventarioDao {

    @Insert
    Long insertarMovimientoInventario(MovimientoInventario movimientoInventario);

    @Update
    int actualizarMovimientoInventario(MovimientoInventario movimientoInventario);

    @Delete
    int eliminarMovimientoInventario(MovimientoInventario movimientoInventario);

    @Query("SELECT * FROM movimientos_inventario")
    List<MovimientoInventario> obtenerTodosLosMovimientos();

    @Query("SELECT * FROM movimientos_inventario WHERE id = :id")
    MovimientoInventario obtenerMovimientoPorId(int id);

    // Obtener MovimientoInventario con su Producto
    @Transaction
    @Query("SELECT * FROM movimientos_inventario")
    List<MovimientoInventarioPojo> obtenerMovimientosConProducto();
}

