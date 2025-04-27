package com.evervc.stepkeeper.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.evervc.stepkeeper.models.Cliente;
import com.evervc.stepkeeper.models.Empleado;
import com.evervc.stepkeeper.models.Venta;

public class VentaPojo {
    @Embedded
    public Venta venta;

    @Relation(
            parentColumn = "id_cliente",
            entityColumn = "id"
    )
    public Cliente cliente;

    @Relation(
            parentColumn = "id_empleado",
            entityColumn = "id"
    )
    public Empleado empleado;
}

