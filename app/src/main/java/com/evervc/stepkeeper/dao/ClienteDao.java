package com.evervc.stepkeeper.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.evervc.stepkeeper.models.Cliente;

import java.util.List;

@Dao
public interface ClienteDao {

    @Insert
    Long insertarCliente(Cliente cliente);

    @Query("SELECT * FROM clientes")
    List<Cliente> obtenerTodosLosClientes();

    @Query("SELECT * FROM clientes WHERE id = :id")
    Cliente obtenerClientePorId(int id);

    @Update
    int actualizarCliente(Cliente cliente);

    @Delete
    int eliminarCliente(Cliente cliente);
}

