package com.evervc.stepkeeper.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.evervc.stepkeeper.models.DetalleVenta;
import com.evervc.stepkeeper.models.Producto;
import com.evervc.stepkeeper.models.Venta;

public class DetalleVentaPojo {
    @Embedded
    public DetalleVenta detalleVenta;

    @Relation(
            parentColumn = "id_producto",
            entityColumn = "id"
    )
    public Producto producto;

    @Relation(
            parentColumn = "id_venta",
            entityColumn = "id"
    )
    public Venta venta;
}
