package com.evervc.stepkeeper.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.evervc.stepkeeper.models.Categoria;
import com.evervc.stepkeeper.models.Marca;
import com.evervc.stepkeeper.models.Producto;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductosActivity extends AppCompatActivity {
    private int idProductoAEditar = -1;
    private TextView tvTituloActivityProductos;
    private EditText etNombreProducto, etTallaProducto, etPrecioProducto;
    private Spinner spCategoria, spMarca;
    private Button btnAgregarProducto;
    private ZapateriaDatabase db;
    private List<Categoria> lstCategorias;
    private List<Marca> lstMarcas;
    private Categoria categoriaDeProducto = null;
    private Marca marcaDeProducto = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_productos);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = ZapateriaDatabase.getInstancia(this);

        idProductoAEditar = getIntent().getIntExtra("idProductoAEditar", -1);

        asociarElementosXml();

        // Carga la informacion en los Spinners
        cargarCategorias();
        cargarMarcas();

        if (idProductoAEditar != -1) {
            tvTituloActivityProductos.setText("ACTUALIZAR PRODUCTO");
            btnAgregarProducto.setText("Actualizar");
            btnAgregarProducto.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icono_editar, 0, 0 ,0);

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                Producto producto = db.productoDao().obtenerProductoPorId(idProductoAEditar);
                if (producto != null) {
                    runOnUiThread(() -> {
                        // Cargar la informacion en los EdiText
                        etNombreProducto.setText(producto.getNombre());
                        etTallaProducto.setText(producto.getTalla());
                        etPrecioProducto.setText(String.valueOf(producto.getPrecio()));

                        // Selecciona la categoria y marca
                        seleccionarCategoriaDelProductoAEditar(producto.getIdCategoria());
                        seleccionarMarcaDelProductoAEditar(producto.getIdMarca());
                    });
                }
            });
        }

        spMarca.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                marcaDeProducto = (Marca) adapterView.getSelectedItem();
                if (marcaDeProducto.getNombre().equals("--> No hay marcas registrados <--"))
                    marcaDeProducto = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                categoriaDeProducto = (Categoria) adapterView.getSelectedItem();
                if (categoriaDeProducto.getNombre().equals("--> No hay categorias registrados <--"))
                    categoriaDeProducto = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void asociarElementosXml() {
        tvTituloActivityProductos = findViewById(R.id.tvTituloActivityProductos);
        etNombreProducto = findViewById(R.id.etNombreProducto);
        etTallaProducto = findViewById(R.id.etTallaProducto);
        etPrecioProducto = findViewById(R.id.etPrecioProducto);
        spCategoria = findViewById(R.id.spCategoria);
        spMarca = findViewById(R.id.spMarca);
        btnAgregarProducto = findViewById(R.id.btnAgregarProducto);
    }

    private void cargarCategorias() {
        Handler handler = new Handler(Looper.getMainLooper());
        // Obteniendo los valores insertados en Categorias
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            lstCategorias = db.categoriaDao().obtenerTodasLasCategorias();
            handler.post(() -> {
                if (lstCategorias.isEmpty()) {
                    lstCategorias.add(new Categoria("--> No hay categorias registrados <--"));
                    spCategoria.setEnabled(false);
                    spCategoria.setClickable(false);
                    spCategoria.setFocusable(false);

                } else {
                    spCategoria.setEnabled(true);
                    spCategoria.setClickable(true);
                    spCategoria.setFocusable(true);
                }
                ArrayAdapter<Categoria> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstCategorias);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCategoria.setAdapter(arrayAdapter);
            });
        });
    }

    private void cargarMarcas() {
        Handler handler = new Handler(Looper.getMainLooper());
        // Obteniendo los valores insertados en Categorias
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            lstMarcas = db.marcaDao().obtenerTodasLasMarcas();
            handler.post(() -> {
                if (lstMarcas.isEmpty()) {
                    lstMarcas.add(new Marca("--> No hay marcas registrados <--"));
                    spMarca.setEnabled(false);
                    spMarca.setClickable(false);
                    spMarca.setFocusable(false);

                } else {
                    spMarca.setEnabled(true);
                    spMarca.setClickable(true);
                    spMarca.setFocusable(true);

                }
                ArrayAdapter<Marca> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, lstMarcas);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spMarca.setAdapter(arrayAdapter);
            });
        });
    }

    private void seleccionarCategoriaDelProductoAEditar(int idCategoriaDelProducto) {
        spCategoria.post(() -> {
            if (lstCategorias == null || lstCategorias.isEmpty()) return;

            for (int i = 0; i < lstCategorias.size(); i++) {
                if (lstCategorias.get(i).getId() == idCategoriaDelProducto) {
                    spCategoria.setSelection(i);
                    categoriaDeProducto = lstCategorias.get(i);
                    break;
                }
            }
        });
    }

    private void seleccionarMarcaDelProductoAEditar(int idMarcaDelProducto) {
        spMarca.post(() -> {
            if (lstMarcas == null || lstMarcas.isEmpty()) return;

            for (int i = 0; i < lstMarcas.size(); i++) {
                if (lstMarcas.get(i).getId() == idMarcaDelProducto) {
                    spMarca.setSelection(i);
                    marcaDeProducto = lstMarcas.get(i);
                    break;
                }
            }
        });
    }



    public void agregarProducto(View view) {
        if (marcaDeProducto == null) {
            Toast.makeText(this, "Debe seleccionar la marca.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (categoriaDeProducto == null) {
            Toast.makeText(this, "Debe seleccionar la categoria", Toast.LENGTH_SHORT).show();
            return;
        }

        if (etNombreProducto.getText().toString().isEmpty() || etTallaProducto.getText().toString().isEmpty() || etPrecioProducto.getText().toString().isEmpty()) {
            Toast.makeText(this, "Debe rellenar todos los campos que contienen [*]", Toast.LENGTH_SHORT).show();
            return;
        }

        double precio = Double.valueOf(etPrecioProducto.getText().toString());

        Producto nuevoProducto = new Producto(etNombreProducto.getText().toString(), etTallaProducto.getText().toString(), precio, categoriaDeProducto.getId(), marcaDeProducto.getId());

        if (idProductoAEditar != -1) {
            // Actualizar Producto
            nuevoProducto.setId(idProductoAEditar);
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                int resultado = db.productoDao().actualizarProducto(nuevoProducto);
                if (resultado > 0) {
                    setResult(RESULT_OK);
                    finish();
                }
            });
        } else {
            // Crear nuevo producto
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                Long resultado = db.productoDao().insertarProducto(nuevoProducto);
                if (resultado > 0) {
                    setResult(RESULT_OK);
                    finish();
                }
            });
        }
    }

    public void cerrarProductosActivity(View view) {
        finish();
    }

    public void gestionarCategorias(View view) {
        Intent gestionCategorias = new Intent(this, CategoriasActivity.class);
        startActivity(gestionCategorias);
    }

    public void gestionarMarcas(View view) {
        Intent gestionMarcas = new Intent(this, MarcasActivity.class);
        startActivity(gestionMarcas);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        // Carga la informacion en los Spinners cada vez que regresan al activitty
        cargarCategorias();
        cargarMarcas();
    }
}