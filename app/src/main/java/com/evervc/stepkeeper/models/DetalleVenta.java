package com.evervc.stepkeeper.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "detalle_ventas",
        foreignKeys = {
                @ForeignKey(
                        entity = Venta.class,
                        parentColumns = "id",
                        childColumns = "id_venta",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Producto.class,
                        parentColumns = "id",
                        childColumns = "id_producto",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "id_venta"),
                @Index(value = "id_producto")
        }
)
public class DetalleVenta {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "cantidad")
    private int cantidad;

    @ColumnInfo(name = "precio_unitario")
    private double precioUnitario;

    @ColumnInfo(name = "subtotal")
    private double subtotal;

    @ColumnInfo(name = "id_venta")
    private int idVenta;

    @ColumnInfo(name = "id_producto")
    private int idProducto;

    public DetalleVenta() {
    }

    public DetalleVenta(int cantidad, double precioUnitario, double subtotal, int idVenta, int idProducto) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
}
