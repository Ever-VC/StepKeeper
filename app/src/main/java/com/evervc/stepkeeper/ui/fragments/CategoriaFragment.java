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
import com.evervc.stepkeeper.models.Categoria;
import com.evervc.stepkeeper.ui.CategoriasActivity;
import com.evervc.stepkeeper.ui.adapters.CategoriaAdaptador;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoriaFragment extends Fragment {
    private RecyclerView rcvCategorias;
    private Button btnNuevoCategoria;
    private TextView tvMensajeInfo;
    private ActivityResultLauncher<Intent> activityLauncherCategorias;

    public CategoriaFragment() {
    }
    public static CategoriaFragment newInstance(String param1, String param2) {
        CategoriaFragment fragment = new CategoriaFragment();
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
        View view = inflater.inflate(R.layout.activity_gestionar_categorias, container, false);

        asociarElementosXml(view);

        actualizarListaCategorias();

        activityLauncherCategorias = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Actualizar el recyclervire
                        actualizarListaCategorias();
                    }
                }
        );

        btnNuevoCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevoCategoria = new Intent(getContext(), CategoriasActivity.class);
                activityLauncherCategorias.launch(nuevoCategoria);
            }
        });

        return view;
    }

    private void asociarElementosXml(View view) {
        rcvCategorias = view.findViewById(R.id.rcvCategorias);
        btnNuevoCategoria = view.findViewById(R.id.btnNuevoCategoria);
        tvMensajeInfo = view.findViewById(R.id.tvMensajeInfo);
    }
    private void actualizarListaCategorias() {
        // Extrae todos los categorias de la base de datos y los muestra en el recyclerview
        ZapateriaDatabase db = ZapateriaDatabase.getInstancia(getContext());

        Handler handler = new Handler(Looper.getMainLooper());

        // Obteniendo los valores insertados
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<Categoria> lstCategorias = db.categoriaDao().obtenerTodasLasCategorias();
            handler.post(() -> {
                // Muestra el mensaje de que no hay clientes registrados segun sea el caso
                if (!lstCategorias.isEmpty()) {
                    tvMensajeInfo.setVisibility(View.GONE);
                    System.out.println("La NO lista esta vacia");
                } else {
                    System.out.println("La lista esta vacia");
                    tvMensajeInfo.setVisibility(View.VISIBLE);
                }

                CategoriaAdaptador categoriaAdaptador = new CategoriaAdaptador(lstCategorias, getContext(), this::actualizarListaCategorias, activityLauncherCategorias);
                rcvCategorias.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
                rcvCategorias.setAdapter(categoriaAdaptador);
            });
        });
    }
}
