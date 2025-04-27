package com.evervc.stepkeeper.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.evervc.stepkeeper.models.MovimientoInventario;
import com.evervc.stepkeeper.models.Producto;

public class MovimientoInventarioPojo {
    @Embedded
    public MovimientoInventario movimientoInventario;

    @Relation(
            parentColumn = "id_producto",
            entityColumn = "id"
    )
    public Producto producto;
}

