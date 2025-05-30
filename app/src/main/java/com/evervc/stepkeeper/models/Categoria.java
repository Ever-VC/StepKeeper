package com.evervc.stepkeeper.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "categorias")
public class Categoria {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    // Constructor vacío
    public Categoria() {
    }

    // Por si necesitamos crear objetos de forma rápida
    @Ignore
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
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

    @Override
    public String toString() {
        return nombre;
    }
}