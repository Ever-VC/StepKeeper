package com.evervc.stepkeeper.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.evervc.stepkeeper.ci.ConvertidorTipoMovimiento;
import com.evervc.stepkeeper.enums.TipoMovimiento;

@Entity(
        tableName = "movimientos_inventario",
        foreignKeys = {
                @ForeignKey(
                        entity = Producto.class,
                        parentColumns = "id",
                        childColumns = "id_producto",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "id_producto")
        }
)
public class MovimientoInventario {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @TypeConverters(ConvertidorTipoMovimiento.class) // Le indica el convertidor para el ENUM
    @ColumnInfo(name = "tipo")
    private TipoMovimiento tipo;

    @ColumnInfo(name = "cantidad")
    private int cantidad;

    @ColumnInfo(name = "precio_unitario")
    private double precioUnitario;

    @ColumnInfo(name = "fecha")
    private long fecha;

    @ColumnInfo(name = "id_producto")
    private int idProducto;

    public MovimientoInventario() {
    }

    public MovimientoInventario(TipoMovimiento tipo, int cantidad, double precioUnitario, long fecha, int idProducto) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.fecha = fecha;
        this.idProducto = idProducto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
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

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }
}
