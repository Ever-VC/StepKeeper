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

import com.evervc.stepkeeper.R;
import com.evervc.stepkeeper.database.ZapateriaDatabase;
import com.evervc.stepkeeper.pojo.DetalleVentaPojo;
import com.evervc.stepkeeper.pojo.MovimientoInventarioPojo;
import com.evervc.stepkeeper.ui.VentasActivity;
import com.evervc.stepkeeper.ui.adapters.MovimientoAdaptador;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InventarioFragment extends Fragment {
    private RecyclerView rcvMovimientos;
    private Button btnNuevaSalida;
    private ActivityResultLauncher<Intent> activityLauncherMovimientos;

    public InventarioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        actualizarElementos();
    }

    public static InventarioFragment newInstance(String param1, String param2) {
        InventarioFragment fragment = new InventarioFragment();
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
        View view = inflater.inflate(R.layout.fragment_inventario, container, false);
        asociarElementosXml(view);
        actualizarElementos();

        activityLauncherMovimientos = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Actualizar el recyclervire
                        actualizarElementos();
                    }
                }
        );

        btnNuevaSalida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent venta = new Intent(getContext(), VentasActivity.class);
                startActivity(venta);
            }
        });


        return view;
    }

    private void asociarElementosXml(View view) {
        rcvMovimientos = view.findViewById(R.id.rcvMovimientos);
        btnNuevaSalida = view.findViewById(R.id.btnNuevaSalida);
    }

    private void actualizarElementos() {
        // Extrae todos los clientes de la base de datos y los muestra en el recyclerview
        ZapateriaDatabase db = ZapateriaDatabase.getInstancia(getContext());

        Handler handler = new Handler(Looper.getMainLooper());

        // Obteniendo los valores insertados
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<MovimientoInventarioPojo> lstInventario = db.movimientoInventarioDao().obtenerMovimientosConProducto();
            handler.post(() -> {
                MovimientoAdaptador adaptador = new MovimientoAdaptador(lstInventario, getContext(), this::actualizarElementos, activityLauncherMovimientos);
                rcvMovimientos.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
                rcvMovimientos.setAdapter(adaptador);
            });
        });
    }
}