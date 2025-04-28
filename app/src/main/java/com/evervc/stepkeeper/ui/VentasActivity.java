package com.evervc.stepkeeper.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.evervc.stepkeeper.R;
import com.evervc.stepkeeper.database.ZapateriaDatabase;
import com.evervc.stepkeeper.enums.TipoMovimiento;
import com.evervc.stepkeeper.models.Cliente;
import com.evervc.stepkeeper.models.DetalleVenta;
import com.evervc.stepkeeper.models.Empleado;
import com.evervc.stepkeeper.models.MovimientoInventario;
import com.evervc.stepkeeper.models.Producto;
import com.evervc.stepkeeper.models.Venta;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VentasActivity extends AppCompatActivity {
    private Spinner spProductos, spEmpleados, spClientes;
    private EditText etCantidadVenta;
    private TextView tvPrecioUnitario, tvPrecioSubTotal, tvPrecioTotal;
    private List<Producto> lstProductos;
    private List<Empleado> lstEmpleados;
    private List<Cliente> lstClientes;
    private ZapateriaDatabase db;
    private int cantidadVenta = 0;
    private double subTotal, total;
    private Producto productoSeleccionado = null;
    private Empleado empleadoSelecionado = null;
    private Cliente clienteSeleccionado = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = ZapateriaDatabase.getInstancia(this);

        asociarElementosXml();

        cargarProductos();

        cargarEmpleados();

        cargarClientes();

        etCantidadVenta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println("===============>>>> SE EESTA MODIFCANDO");
                String texto = charSequence.toString();
                if (!texto.isEmpty()) {
                    try {
                        cantidadVenta = Integer.parseInt(texto);
                        subTotal = productoSeleccionado.getPrecio() * cantidadVenta;
                        total = subTotal;
                        tvPrecioSubTotal.setText("Sub total: $" + String.valueOf(subTotal));
                        tvPrecioTotal.setText("Total: $" + String.valueOf(subTotal));
                    } catch (NumberFormatException e) {
                        cantidadVenta = 0; // Si escribe algo inválido, se reseteamos
                    }
                } else {
                    cantidadVenta = 0; // Si está vacío, ponemos 0.0 de nuevo xd
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        spProductos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Producto producto = (Producto) adapterView.getSelectedItem();
                if (producto != null) {
                    productoSeleccionado = producto;
                    runOnUiThread(() -> {
                        tvPrecioUnitario.setText("Precio unitario: $" + String.valueOf(producto.getPrecio()));
                    });
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spEmpleados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                empleadoSelecionado = (Empleado) adapterView.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        spClientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                clienteSeleccionado = (Cliente) adapterView.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void asociarElementosXml() {
        spProductos = findViewById(R.id.spProductos);
        spEmpleados = findViewById(R.id.spEmpleados);
        spClientes = findViewById(R.id.spClientes);
        etCantidadVenta = findViewById(R.id.etCantidadVenta);
        tvPrecioUnitario = findViewById(R.id.tvPrecioUnitario);
        tvPrecioSubTotal = findViewById(R.id.tvPrecioSubTotal);
        tvPrecioTotal = findViewById(R.id.tvPrecioTotal);
    }

    private void cargarProductos() {
        Handler handler = new Handler(Looper.getMainLooper());
        // Obteniendo los valores insertados en Productos
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            lstProductos = db.productoDao().obtenerTodosLosProductos();
            handler.post(() -> {
                if (lstProductos.isEmpty()) {
                    Toast.makeText(this, "Primero debe agregar un producto para poder vender o reabastecerlo.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    ArrayAdapter<Producto> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstProductos);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spProductos.setAdapter(arrayAdapter);
                }
            });
        });
    }

    private void cargarEmpleados() {
        Handler handler = new Handler(Looper.getMainLooper());
        // Obteniendo los valores insertados en Empleados
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            lstEmpleados = db.empleadoDao().obtenerTodosLosEmpleados();
            handler.post(() -> {
                if (lstEmpleados.isEmpty()) {
                    Toast.makeText(this, "Primero debe agregar un empleado para poder asociarle una venta.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    ArrayAdapter<Empleado> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstEmpleados);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spEmpleados.setAdapter(arrayAdapter);
                }
            });
        });
    }

    private void cargarClientes() {
        Handler handler = new Handler(Looper.getMainLooper());
        // Obteniendo los valores insertados en Empleados
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            lstClientes = db.clienteDao().obtenerTodosLosClientes();
            handler.post(() -> {
                if (lstClientes.isEmpty()) {
                    Toast.makeText(this, "Primero debe agregar un cliente para poder asociarle una venta.", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    ArrayAdapter<Cliente> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstClientes);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spClientes.setAdapter(arrayAdapter);
                }
            });
        });
    }

    public void agregarVenta(View view) {
        Venta nuevaVenta = new Venta();
        nuevaVenta.setFecha(System.currentTimeMillis());
        nuevaVenta.setIdCliente(clienteSeleccionado.getId());
        nuevaVenta.setIdEmpleado(empleadoSelecionado.getId());
        nuevaVenta.setTotal(total);

        DetalleVenta nuevoDetalleVenta = new DetalleVenta();
        nuevoDetalleVenta.setIdVenta(nuevaVenta.getId());
        nuevoDetalleVenta.setCantidad(Integer.parseInt(etCantidadVenta.getText().toString()));
        nuevoDetalleVenta.setIdProducto(productoSeleccionado.getId());
        nuevoDetalleVenta.setSubtotal(subTotal);
        nuevoDetalleVenta.setPrecioUnitario(productoSeleccionado.getPrecio());

        MovimientoInventario movimientoInventario = new MovimientoInventario();
        movimientoInventario.setIdProducto(productoSeleccionado.getId());
        movimientoInventario.setTipo(TipoMovimiento.SALIDA);
        movimientoInventario.setCantidad(Integer.parseInt(etCantidadVenta.getText().toString()));
        movimientoInventario.setPrecioUnitario(productoSeleccionado.getPrecio());
        movimientoInventario.setFecha(System.currentTimeMillis());

        /*ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            Long resultado1 = db.ventaDao().insertarVenta(nuevaVenta);
            Long resultado2 = db.detalleVentaDao().insertarDetalleVenta(nuevoDetalleVenta);
            Long resultado3 = db.movimientoInventarioDao().insertarMovimientoInventario(movimientoInventario);

            if (resultado1 > 0 && resultado2 > 0 && resultado3 > 0) {
                setResult(RESULT_OK);
                finish();
            }
        });*/
    }













}