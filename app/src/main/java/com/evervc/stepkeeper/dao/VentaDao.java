package com.evervc.stepkeeper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.evervc.stepkeeper.models.Venta;
import com.evervc.stepkeeper.pojo.VentaPojo;

import java.util.List;

@Dao
public interface VentaDao {

    @Insert
    Long insertarVenta(Venta venta);

    @Update
    int actualizarVenta(Venta venta);

    @Delete
    int eliminarVenta(Venta venta);

    @Query("SELECT * FROM ventas")
    List<Venta> obtenerTodasLasVentas();

    @Query("SELECT * FROM ventas WHERE id = :id")
    Venta obtenerVentaPorId(int id);

    // Obtener Venta con Cliente y Empleado
    @Transaction
    @Query("SELECT * FROM ventas")
    List<VentaPojo> obtenerVentasConClienteYEmpleado();
}


