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
import com.evervc.stepkeeper.models.Empleado;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmpleadosActivity extends AppCompatActivity {
    private int idEmpleadoAEditar = -1;
    private TextView tvTituloActivityEmpleados;
    private EditText etNombreEmpleado, etPuestoEmpleado;
    private Button btnAgregarEmpleado;
    private ZapateriaDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_empleados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = ZapateriaDatabase.getInstancia(this);

        idEmpleadoAEditar = getIntent().getIntExtra("idEmpleadoAEditar", -1);

        asociarElementosXml();

        if (idEmpleadoAEditar != -1) {
            tvTituloActivityEmpleados.setText("EDITAR EMPLEADO");
            btnAgregarEmpleado.setText("Actualizar");
            btnAgregarEmpleado.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icono_editar, 0, 0 ,0);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                Empleado empleado = db.empleadoDao().obtenerEmpleadoPorId(idEmpleadoAEditar);
                if (empleado != null) {
                    runOnUiThread(() -> {
                        // Cargar la informacion en los EdiText
                        etNombreEmpleado.setText(empleado.getNombre());
                        etPuestoEmpleado.setText(empleado.getPuesto());
                    });
                }
            });
        }
    }
    private void asociarElementosXml() {
        tvTituloActivityEmpleados = findViewById(R.id.tvTituloActivityEmpleados);
        etNombreEmpleado = findViewById(R.id.etNombreEmpleado);
        etPuestoEmpleado = findViewById(R.id.etPuestoEmpleado);
        btnAgregarEmpleado = findViewById(R.id.btnAgregarEmpleado);
    }

    public void agregarEmpleado(View view) {
        // Validaciones generales
        if (etNombreEmpleado.getText().toString().isEmpty() ||  etPuestoEmpleado.getText().toString().isEmpty()) {
            Toast.makeText(this, "Asegurese de rellenar todos los campos.", Toast.LENGTH_SHORT).show();
        }

        Empleado nuevoEmpleado = new Empleado(etNombreEmpleado.getText().toString(), etPuestoEmpleado.getText().toString());

        if (idEmpleadoAEditar == -1) {
            // Crea un nuevo cliente
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                Long resultado = db.empleadoDao().insertarEmpleado(nuevoEmpleado);
                if (resultado > 0) {
                    setResult(RESULT_OK);
                    finish();
                }
            });
        } else {
            // Antualiza el cliente con idClienteAEditar
            nuevoEmpleado.setId(idEmpleadoAEditar);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                int resultado = db.empleadoDao().actualizarEmpleado(nuevoEmpleado);
                if (resultado > 0) {
                    setResult(RESULT_OK);
                    finish();
                }
            });
        }
    }

    public void cerrarEmpleadosActivity(View view) {
        finish();
    }
}