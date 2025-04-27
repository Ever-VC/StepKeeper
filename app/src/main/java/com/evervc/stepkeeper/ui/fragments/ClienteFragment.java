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
import com.evervc.stepkeeper.ui.ClientesActivity;
import com.evervc.stepkeeper.ui.adapters.ClienteAdaptador;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClienteFragment extends Fragment {
    private RecyclerView rcvClientes;
    private Button btnNuevoCliente;
    private TextView tvMensajeInfo;
    private ActivityResultLauncher<Intent> activityLauncherClientes;


    public ClienteFragment() {
        // Required empty public constructor
    }
    public static ClienteFragment newInstance(String param1, String param2) {
        ClienteFragment fragment = new ClienteFragment();
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
        View view = inflater.inflate(R.layout.fragment_cliente, container, false);

        asociarElementosXml(view);

        actualizarListaClientes();

        activityLauncherClientes = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Actualizar el recyclervire
                        actualizarListaClientes();
                    }
                }
        );

        btnNuevoCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevoCiente = new Intent(getContext(), ClientesActivity.class);
                activityLauncherClientes.launch(nuevoCiente);
            }
        });

        return view;
    }

    private void asociarElementosXml(View view) {
        rcvClientes = view.findViewById(R.id.rcvClientes);
        btnNuevoCliente = view.findViewById(R.id.btnNuevoCliente);
        tvMensajeInfo = view.findViewById(R.id.tvMensajeInfo);
    }

    private void actualizarListaClientes() {
        // Extrae todos los clientes de la base de datos y los muestra en el recyclerview
        ZapateriaDatabase db = ZapateriaDatabase.getInstancia(getContext());

        Handler handler = new Handler(Looper.getMainLooper());

        // Obteniendo los valores insertados
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<Cliente> lstClientes = db.clienteDao().obtenerTodosLosClientes();
            handler.post(() -> {
                // Muestra el mensaje de que no hay clientes registrados segun sea el caso
                if (!lstClientes.isEmpty()) {
                    tvMensajeInfo.setVisibility(View.GONE);
                    System.out.println("La NO lista esta vacia");
                } else {
                    System.out.println("La lista esta vacia");
                    tvMensajeInfo.setVisibility(View.VISIBLE);
                }

                ClienteAdaptador clienteAdaptador = new ClienteAdaptador(lstClientes, getContext(), this::actualizarListaClientes, activityLauncherClientes);
                rcvClientes.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
                rcvClientes.setAdapter(clienteAdaptador);
            });
        });
    }
}