package com.evervc.stepkeeper.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "ventas",
        foreignKeys = {
                @ForeignKey(
                        entity = Cliente.class,
                        parentColumns = "id",
                        childColumns = "id_cliente",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Empleado.class,
                        parentColumns = "id",
                        childColumns = "id_empleado",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "id_cliente"),
                @Index(value = "id_empleado")
        }
)
public class Venta {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "fecha")
    private long fecha;

    @ColumnInfo(name = "total")
    private double total;

    @ColumnInfo(name = "id_cliente")
    private int idCliente;

    @ColumnInfo(name = "id_empleado")
    private int idEmpleado;

    public Venta() {
    }

    public Venta(long fecha, double total, int idCliente, int idEmpleado) {
        this.fecha = fecha;
        this.total = total;
        this.idCliente = idCliente;
        this.idEmpleado = idEmpleado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
}
