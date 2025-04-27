package com.evervc.stepkeeper.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.evervc.stepkeeper.R;
import com.evervc.stepkeeper.database.ZapateriaDatabase;
import com.evervc.stepkeeper.models.Cliente;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientesActivity extends AppCompatActivity {
    private int idClienteAEditar = -1;
    private TextView tvTituloActivityClientes;
    private EditText etNombreCliente, etTelefonoCliente, etCorreoCliente;
    private Button btnAgregarCliente;
    private ZapateriaDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_clientes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = ZapateriaDatabase.getInstancia(this);

        idClienteAEditar = getIntent().getIntExtra("idClienteAEditar", -1);

        asociarElementosXml();

        if (idClienteAEditar != -1) {
            tvTituloActivityClientes.setText("EDITAR CLIENTE");
            btnAgregarCliente.setText("Actualizar");
            btnAgregarCliente.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icono_editar, 0, 0 ,0);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                Cliente cliente = db.clienteDao().obtenerClientePorId(idClienteAEditar);
                if (cliente != null) {
                    runOnUiThread(() -> {
                        // Cargar la informacion en los EdiText
                        etNombreCliente.setText(cliente.getNombre());
                        etTelefonoCliente.setText(cliente.getTelefono());
                        etCorreoCliente.setText(cliente.getCorreo());
                    });
                }
            });
        }
    }

    private void asociarElementosXml() {
        tvTituloActivityClientes = findViewById(R.id.tvTituloActivityClientes);
        etNombreCliente = findViewById(R.id.etNombreCliente);
        etTelefonoCliente = findViewById(R.id.etTelefonoCliente);
        etCorreoCliente = findViewById(R.id.etCorreoCliente);
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente);
    }

    public void agregarCliente(View view) {
        // Validaciones generales
        if (etNombreCliente.getText().toString().isEmpty() || etTelefonoCliente.getText().toString().isEmpty() || etCorreoCliente.getText().toString().isEmpty()) {
            Toast.makeText(this, "Asegurese de rellenar todos los campos.", Toast.LENGTH_SHORT).show();
        }

        Cliente nuevoCliente = new Cliente(etNombreCliente.getText().toString(), etTelefonoCliente.getText().toString(), etCorreoCliente.getText().toString());

        if (idClienteAEditar == -1) {
            // Crea un nuevo cliente
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                Long resultado = db.clienteDao().insertarCliente(nuevoCliente);
                if (resultado > 0) {
                    setResult(RESULT_OK);
                    finish();
                }
            });
        } else {
            // Antualiza el cliente con idClienteAEditar
            nuevoCliente.setId(idClienteAEditar);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                int resultado = db.clienteDao().actualizarCliente(nuevoCliente);
                if (resultado > 0) {
                    setResult(RESULT_OK);
                    finish();
                }
            });
        }
    }

    public void cerrarClientesActivity(View view) {
        finish();
    }
}