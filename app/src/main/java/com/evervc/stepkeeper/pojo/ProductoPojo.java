package com.evervc.stepkeeper.pojo;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.evervc.stepkeeper.models.Categoria;
import com.evervc.stepkeeper.models.Producto;

public class ProductoPojo {
    @Embedded
    public Producto producto;

    @Relation(
            parentColumn = "id_categoria",
            entityColumn = "id"
    )
    public Categoria categoria;
}

