package com.evervc.stepkeeper.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
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
                )
        },
        indices = {
                @Index(value = "id_categoria")
        }
)
public class Producto {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "marca")
    private String marca;

    @ColumnInfo(name = "talla")
    private String talla;

    @ColumnInfo(name = "precio")
    private double precio;

    @ColumnInfo(name = "id_categoria")
    private int idCategoria;

    // Constructor vacío
    public Producto() {
    }

    // Por si necesitamos crear objetos de forma rápida
    public Producto(String nombre, String marca, String talla, double precio, int idCategoria) {
        this.nombre = nombre;
        this.marca = marca;
        this.talla = talla;
        this.precio = precio;
        this.idCategoria = idCategoria;
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

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
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
}
