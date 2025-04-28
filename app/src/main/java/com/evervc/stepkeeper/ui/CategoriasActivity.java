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
import com.evervc.stepkeeper.models.Categoria;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoriasActivity extends AppCompatActivity {
    private int idCategoriaAEditar;
    private EditText etNombreCategoria;
    private TextView tvTituloActivityCategorias;
    private Button btnAgregarCategoria;
    private ZapateriaDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categorias);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = ZapateriaDatabase.getInstancia(this);

        idCategoriaAEditar  = getIntent().getIntExtra("idCategoriaAEditar", -1);

        asociarElementosXml();

        if (idCategoriaAEditar != -1) {
            tvTituloActivityCategorias.setText("ACTUALIZAR CATEGORIA");
            btnAgregarCategoria.setText("Actualizar");
            btnAgregarCategoria.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icono_editar, 0, 0 ,0);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                Categoria categoria = db.categoriaDao().obtenerCategoriaPorId(idCategoriaAEditar);

                if (categoria != null) {
                    runOnUiThread(() -> {
                        // Cargar la informacion en los EdiText
                        etNombreCategoria.setText(categoria.getNombre());
                    });
                }
            });
        }
    }

    private void asociarElementosXml() {
        etNombreCategoria = findViewById(R.id.etNombreCategoria);
        tvTituloActivityCategorias = findViewById(R.id.tvTituloActivityCategorias);
        btnAgregarCategoria = findViewById(R.id.btnAgregarCategoria);
    }

    public void agregarCategoria(View view) {
        if (etNombreCategoria.getText().toString().isEmpty()) {
            Toast.makeText(this, "Debe agregar el nombre de la categoria", Toast.LENGTH_SHORT).show();
            return;
        }

        Categoria nuevaCategoria = new Categoria(etNombreCategoria.getText().toString());

        if (idCategoriaAEditar != -1) {
            // Antualiza la categoria
            nuevaCategoria.setId(idCategoriaAEditar);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                int resultado = db.categoriaDao().actualizarCategoria(nuevaCategoria);
                if (resultado > 0) {
                    setResult(RESULT_OK);
                    finish();
                }
            });
        } else {
            // Crea una nueva Categoria
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                Long resultado = db.categoriaDao().insertarCategoria(nuevaCategoria);
                if (resultado > 0) {
                    setResult(RESULT_OK);
                    finish();
                }
            });
        }
    }

    public void cerrarCategoriasActivity(View view) {
        finish();
    }
}