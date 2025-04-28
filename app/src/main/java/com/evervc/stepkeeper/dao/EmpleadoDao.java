package com.evervc.stepkeeper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.evervc.stepkeeper.models.Empleado;

import java.util.List;

@Dao
public interface EmpleadoDao {

    @Insert
    Long insertarEmpleado(Empleado empleado);

    @Query("SELECT * FROM empleados")
    List<Empleado> obtenerTodosLosEmpleados();

    @Query("SELECT * FROM empleados WHERE id = :id")
    Empleado obtenerEmpleadoPorId(int id);

    @Update
    int actualizarEmpleado(Empleado empleado);

    @Delete
    int eliminarEmpleado(Empleado empleado);
}

