package com.evervc.stepkeeper.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "productos",
        foreignKeys = {
                @ForeignKey(
                        entity = Categoria.class,
                        parentColumns = "id",
                        childColumns = "id_categoria",
                        onDelete = ForeignKey.CASCADE
                ),
                @ForeignKey(
                        entity = Marca.class,
                        parentColumns = "id",
                        childColumns = "id_marca",
                        onDelete = ForeignKey.CASCADE
                )
        },
        indices = {
                @Index(value = "id_categoria"),
                @Index(value = "id_marca")
        }
)
public class Producto {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "talla")
    private String talla;

    @ColumnInfo(name = "precio")
    private double precio;

    @ColumnInfo(name = "id_categoria")
    private int idCategoria;

    @ColumnInfo(name = "id_marca")
    private int idMarca;

    // Constructor vacío
    public Producto() {
    }

    // Por si necesitamos crear objetos de forma rápida
    @Ignore
    public Producto(String nombre, String talla, double precio, int idCategoria, int idMarca) {
        this.nombre = nombre;
        this.talla = talla;
        this.precio = precio;
        this.idCategoria = idCategoria;
        this.idMarca = idMarca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
