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
import com.evervc.stepkeeper.models.Marca;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MarcasActivity extends AppCompatActivity {
    private int idMarcaAEditar;
    private EditText etNombreMarca;
    private TextView tvTituloActivityMarcas;
    private Button btnAgregarMarca;
    private ZapateriaDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_marcas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = ZapateriaDatabase.getInstancia(this);

        idMarcaAEditar  = getIntent().getIntExtra("idMarcaAEditar", -1);

        asociarElementosXml();

        if (idMarcaAEditar != -1) {
            tvTituloActivityMarcas.setText("ACTUALIZAR MARCA");
            btnAgregarMarca.setText("Actualizar");
            btnAgregarMarca.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icono_editar, 0, 0 ,0);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                Marca marca = db.marcaDao().obtenerMarcaPorId(idMarcaAEditar);
                if (marca != null) {
                    runOnUiThread(() -> {
                        // Cargar la informacion en los EdiText
                        etNombreMarca.setText(marca.getNombre());
                    });
                }
            });
        }
    }

    private void asociarElementosXml() {
        etNombreMarca = findViewById(R.id.etNombreMarca);
        tvTituloActivityMarcas = findViewById(R.id.tvTituloActivityMarcas);
        btnAgregarMarca = findViewById(R.id.btnAgregarMarca);
    }

    public void agregarMarca(View view) {
        if (etNombreMarca.getText().toString().isEmpty()) {
            Toast.makeText(this, "Debe agregar el nombre de la marca.", Toast.LENGTH_SHORT).show();
            return;
        }

        Marca nuevaMarca = new Marca(etNombreMarca.getText().toString());

        if (idMarcaAEditar != -1) {
            // Actualizar la marca
            nuevaMarca.setId(idMarcaAEditar);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
               int resultado = db.marcaDao().actualizarMarca(nuevaMarca);
               if (resultado > 0) {
                   setResult(RESULT_OK);
                   finish();
               }
            });
        } else {
            // Crear la marca
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                Long resultado = db.marcaDao().insertarMarca(nuevaMarca);
                if (resultado > 0) {
                    setResult(RESULT_OK);
                    finish();
                }
            });
        }
    }

    public void cerrarMarcasActivity(View view) {
        finish();
    }
}