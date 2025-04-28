package com.evervc.stepkeeper.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.evervc.stepkeeper.R;
import com.evervc.stepkeeper.database.ZapateriaDatabase;
import com.evervc.stepkeeper.models.Marca;
import com.evervc.stepkeeper.ui.MarcasActivity;
import com.evervc.stepkeeper.ui.adapters.MarcaAdaptador;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MarcaFragment extends Fragment {
    private RecyclerView rcvMarcas;
    private Button btnNuevoMarca;
    private TextView tvMensajeInfo;
    private ActivityResultLauncher<Intent> activityLauncherMarcas;

    public MarcaFragment() {
    }
    public static MarcaFragment newInstance(String param1, String param2) {
        MarcaFragment fragment = new MarcaFragment();
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
        View view = inflater.inflate(R.layout.activity_gestionar_marcas, container, false);

        asociarElementosXml(view);

        actualizarListaMarcas();

        activityLauncherMarcas = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Actualizar el recyclervire
                        actualizarListaMarcas();
                    }
                }
        );

        btnNuevoMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevoMarca = new Intent(getContext(), MarcasActivity.class);
                activityLauncherMarcas.launch(nuevoMarca);
            }
        });

        return view;
    }

    private void asociarElementosXml(View view) {
        rcvMarcas = view.findViewById(R.id.rcvMarcas);
        btnNuevoMarca = view.findViewById(R.id.btnNuevoMarca);
        tvMensajeInfo = view.findViewById(R.id.tvMensajeInfo);
    }
    private void actualizarListaMarcas() {
        // Extrae todos los categorias de la base de datos y los muestra en el recyclerview
        ZapateriaDatabase db = ZapateriaDatabase.getInstancia(getContext());

        Handler handler = new Handler(Looper.getMainLooper());

        // Obteniendo los valores insertados
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<Marca> lstMarcas = db.marcaDao().obtenerTodasLasMarcas();
            handler.post(() -> {
                // Muestra el mensaje de que no hay clientes registrados segun sea el caso
                if (!lstMarcas.isEmpty()) {
                    tvMensajeInfo.setVisibility(View.GONE);
                    System.out.println("La NO lista esta vacia");
                } else {
                    System.out.println("La lista esta vacia");
                    tvMensajeInfo.setVisibility(View.VISIBLE);
                }

                MarcaAdaptador marcaAdaptador = new MarcaAdaptador(lstMarcas, getContext(), this::actualizarListaMarcas, activityLauncherMarcas);
                rcvMarcas.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
                rcvMarcas.setAdapter(marcaAdaptador);
            });
        });
    }
}
