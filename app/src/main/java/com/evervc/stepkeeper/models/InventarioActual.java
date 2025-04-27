package com.evervc.stepkeeper.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "inventario_actual",
        foreignKeys = {
                @ForeignKey(
                        entity = Producto.class,
                        parentColumns = "id",
                        childColumns = "id_producto",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {@Index(value = "id_producto", unique = true)} // Asegura que no haya duplicados de id_producto, por la relacion 1:1
)
public class InventarioActual {

    @PrimaryKey
    @ColumnInfo(name = "id_producto")
    private int idProducto;

    @ColumnInfo(name = "stock")
    private int stock;

    @ColumnInfo(name = "costo_promedio")
    private double costoPromedio;

    public InventarioActual() {
    }

    public InventarioActual(int idProducto, int stock, double costoPromedio) {
        this.idProducto = idProducto;
        this.stock = stock;
        this.costoPromedio = costoPromedio;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getCostoPromedio() {
        return costoPromedio;
    }

    public void setCostoPromedio(double costoPromedio) {
        this.costoPromedio = costoPromedio;
    }
}
