package com.evervc.stepkeeper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.evervc.stepkeeper.models.DetalleVenta;
import com.evervc.stepkeeper.pojo.DetalleVentaPojo;

import java.util.List;

@Dao
public interface DetalleVentaDao {

    @Insert
    Long insertarDetalleVenta(DetalleVenta detalleVenta);

    @Update
    int actualizarDetalleVenta(DetalleVenta detalleVenta);

    @Delete
    int eliminarDetalleVenta(DetalleVenta detalleVenta);

    @Query("SELECT * FROM detalle_ventas WHERE id_venta = :idVenta")
    List<DetalleVenta> obtenerDetallesPorVenta(int idVenta);

    @Query("SELECT * FROM detalle_ventas WHERE id_producto = :idProducto")
    List<DetalleVenta> obtenerDetallesPorProducto(int idProducto);

    @Transaction
    @Query("SELECT * FROM detalle_ventas WHERE id_venta = :idVenta")
    List<DetalleVentaPojo> obtenerDetallesConProductoPorVenta(int idVenta);
}

