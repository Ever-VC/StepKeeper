package com.evervc.stepkeeper;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import com.evervc.stepkeeper.ui.fragments.ClienteFragment;
import com.evervc.stepkeeper.ui.fragments.EmpleadoFragment;
import com.evervc.stepkeeper.ui.fragments.InventarioFragment;
import com.evervc.stepkeeper.ui.fragments.ProductoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navegacionInferior;
    private FragmentContainerView contenedorDeFragmentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        asociarElementosXml();

        navegacionInferior.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return alSeleccionarItem(item);
            }
        });
    }

    private void asociarElementosXml() {
        navegacionInferior = findViewById(R.id.navegacionInferior);
        contenedorDeFragmentos = findViewById(R.id.contenedorDeFragmentos);
    }

    private void reemplazarFragmento(Fragment fragmento) {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorDeFragmentos, fragmento).commit();
    }

    private boolean alSeleccionarItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnInventario:
                reemplazarFragmento(new InventarioFragment());
                return true;
            case R.id.btnProductos:
                reemplazarFragmento(new ProductoFragment());
                return true;
            case R.id.btnClientes:
                reemplazarFragmento(new ClienteFragment());
                return true;
            case R.id.btnEmpleados:
                reemplazarFragmento(new EmpleadoFragment());
                return true;
        }
        return  false;
    }
}