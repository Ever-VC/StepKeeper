package com.evervc.stepkeeper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.evervc.stepkeeper.models.Marca;

import java.util.List;

@Dao
public interface MarcaDao {
    @Insert
    Long insertarMarca(Marca marca);

    @Query("SELECT * FROM marcas")
    List<Marca> obtenerTodasLasMarcas();

    @Query("SELECT * FROM marcas WHERE id = :id")
    Marca obtenerMarcaPorId(int id);

    @Update
    int actualizarMarca(Marca marca);

    @Delete
    int eliminarMarca(Marca marca);
}
