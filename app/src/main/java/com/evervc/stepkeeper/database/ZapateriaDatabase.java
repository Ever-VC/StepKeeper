package com.evervc.stepkeeper.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.evervc.stepkeeper.ci.ConvertidorTipoMovimiento;
import com.evervc.stepkeeper.dao.CategoriaDao;
import com.evervc.stepkeeper.dao.ClienteDao;
import com.evervc.stepkeeper.dao.DetalleVentaDao;
import com.evervc.stepkeeper.dao.EmpleadoDao;
import com.evervc.stepkeeper.dao.InventarioActualDao;
import com.evervc.stepkeeper.dao.MovimientoInventarioDao;
import com.evervc.stepkeeper.dao.ProductoDao;
import com.evervc.stepkeeper.dao.VentaDao;
import com.evervc.stepkeeper.models.Categoria;
import com.evervc.stepkeeper.models.Cliente;
import com.evervc.stepkeeper.models.DetalleVenta;
import com.evervc.stepkeeper.models.Empleado;
import com.evervc.stepkeeper.models.InventarioActual;
import com.evervc.stepkeeper.models.Marca;
import com.evervc.stepkeeper.models.MovimientoInventario;
import com.evervc.stepkeeper.models.Producto;
import com.evervc.stepkeeper.models.Venta;

@Database(
        entities = {
                Categoria.class,
                Marca.class,
                Producto.class,
                Cliente.class,
                Empleado.class,
                Venta.class,
                DetalleVenta.class,
                MovimientoInventario.class,
                InventarioActual.class
        },
        version = 1,
        exportSchema = false
)
@TypeConverters(ConvertidorTipoMovimiento.class)
public abstract class ZapateriaDatabase extends RoomDatabase {

    // Métodos abstractos para acceder a los DAOs y hacer el CRUD
    public abstract CategoriaDao categoriaDao();
    public abstract ProductoDao productoDao();
    public abstract ClienteDao clienteDao();
    public abstract EmpleadoDao empleadoDao();
    public abstract VentaDao ventaDao();
    public abstract DetalleVentaDao detalleVentaDao();
    public abstract MovimientoInventarioDao movimientoInventarioDao();
    public abstract InventarioActualDao inventarioActualDao();

    // Instancia de la base de datos (patrón Singleton)
    private static volatile ZapateriaDatabase INSTANCIA;

    public static synchronized ZapateriaDatabase getInstancia(Context context) {
        if (INSTANCIA == null) {
            synchronized (ZapateriaDatabase.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context.getApplicationContext(),
                                    ZapateriaDatabase.class, "zapateria_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCIA;
    }
}

