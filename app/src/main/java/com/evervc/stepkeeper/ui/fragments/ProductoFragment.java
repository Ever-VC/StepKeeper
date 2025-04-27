package com.evervc.stepkeeper.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.evervc.stepkeeper.R;
import com.evervc.stepkeeper.database.ZapateriaDatabase;
import com.evervc.stepkeeper.models.Cliente;
import com.evervc.stepkeeper.models.Producto;
import com.evervc.stepkeeper.pojo.ProductoPojo;
import com.evervc.stepkeeper.ui.ProductosActivity;
import com.evervc.stepkeeper.ui.adapters.ClienteAdaptador;
import com.evervc.stepkeeper.ui.adapters.ProductoAdaptador;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductoFragment extends Fragment {
    private RecyclerView rcvProductos;
    private Button btnNuevoProducto;
    private TextView tvMensajeInfoProductos;
    private ActivityResultLauncher<Intent> activityLauncherProductos;

    public ProductoFragment() {
        // Required empty public constructor
    }
    public static ProductoFragment newInstance(String param1, String param2) {
        ProductoFragment fragment = new ProductoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_producto, container, false);

        asociarElementosXml(view);

        actualizarListaProductos();

        activityLauncherProductos = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Actualizar el recyclervire
                        actualizarListaProductos();
                    }
                }
        );

        btnNuevoProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevoProducto = new Intent(getContext(), ProductosActivity.class);
                activityLauncherProductos.launch(nuevoProducto);
            }
        });

        return view;
    }

    private void asociarElementosXml(View view) {
        rcvProductos = view.findViewById(R.id.rcvProductos);
        btnNuevoProducto = view.findViewById(R.id.btnNuevoProducto);
        tvMensajeInfoProductos = view.findViewById(R.id.tvMensajeInfoProductos);
    }

    private void actualizarListaProductos() {
        // Extrae todos los clientes de la base de datos y los muestra en el recyclerview
        ZapateriaDatabase db = ZapateriaDatabase.getInstancia(getContext());

        Handler handler = new Handler(Looper.getMainLooper());

        // Obteniendo los valores insertados
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<ProductoPojo> lstProductos = db.productoDao().obtenerProductosConCategoriaYMarca();
            handler.post(() -> {
                // Muestra el mensaje de que no hay clientes registrados segun sea el caso
                if (lstProductos.isEmpty()) {
                    tvMensajeInfoProductos.setVisibility(View.VISIBLE);
                } else {
                    tvMensajeInfoProductos.setVisibility(View.GONE);
                }

                ProductoAdaptador productoAdaptador = new ProductoAdaptador(lstProductos, getContext(), this::actualizarListaProductos, activityLauncherProductos);
                rcvProductos.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
                rcvProductos.setAdapter(productoAdaptador);
            });
        });
    }
}