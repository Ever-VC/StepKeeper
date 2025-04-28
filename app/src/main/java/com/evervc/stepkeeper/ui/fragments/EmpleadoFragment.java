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
import com.evervc.stepkeeper.models.Empleado;
import com.evervc.stepkeeper.ui.ClientesActivity;
import com.evervc.stepkeeper.ui.EmpleadosActivity;
import com.evervc.stepkeeper.ui.adapters.EmpleadoAdaptador;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmpleadoFragment extends Fragment {
    private RecyclerView rcvEmpleados;
    private Button btnNuevoEmpleado;
    private TextView tvMensajeInfo;
    private ActivityResultLauncher<Intent> activityLauncherEmpleados;

    public EmpleadoFragment() {
        // Required empty public constructor
    }

    public static EmpleadoFragment newInstance(String param1, String param2) {
        EmpleadoFragment fragment = new EmpleadoFragment();
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
        View view = inflater.inflate(R.layout.fragment_empleado, container, false);

        asociarElementosXml(view);

        actualizarListaEmpleados();
        activityLauncherEmpleados = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // Actualizar el recyclervire
                        actualizarListaEmpleados();
                    }
                }
        );

        btnNuevoEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nuevoEmpleado = new Intent(getContext(), EmpleadosActivity.class);
                activityLauncherEmpleados.launch(nuevoEmpleado);
            }
        });
        return view;
    }
    private void asociarElementosXml(View view) {
        rcvEmpleados = view.findViewById(R.id.rcvEmpleados);
        btnNuevoEmpleado = view.findViewById(R.id.btnNuevoEmpleado);
        tvMensajeInfo = view.findViewById(R.id.tvMensajeInfo);
    }

    private void actualizarListaEmpleados() {
        // Extrae todos los empleados de la base de datos y los muestra en el recyclerview
        ZapateriaDatabase db = ZapateriaDatabase.getInstancia(getContext());

        Handler handler = new Handler(Looper.getMainLooper());

        // Obteniendo los valores insertados
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            List<Empleado> lstEmpleados = db.empleadoDao().obtenerTodosLosEmpleados();
            handler.post(() -> {
                // Muestra el mensaje de que no hay empleados registrados segun sea el caso
                if (!lstEmpleados.isEmpty()) {
                    tvMensajeInfo.setVisibility(View.GONE);
                } else {
                    System.out.println("La lista esta vacia");
                    tvMensajeInfo.setVisibility(View.VISIBLE);
                }

                EmpleadoAdaptador empleadoAdaptador = new EmpleadoAdaptador(lstEmpleados, getContext(), this::actualizarListaEmpleados, activityLauncherEmpleados);
                rcvEmpleados.setLayoutManager(new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false));
                rcvEmpleados.setAdapter(empleadoAdaptador);
            });
        });
    }
}