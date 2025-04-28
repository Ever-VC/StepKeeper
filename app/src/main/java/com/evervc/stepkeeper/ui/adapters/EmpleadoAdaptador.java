package com.evervc.stepkeeper.ui.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evervc.stepkeeper.R;
import com.evervc.stepkeeper.database.ZapateriaDatabase;
import com.evervc.stepkeeper.models.Cliente;
import com.evervc.stepkeeper.models.Empleado;
import com.evervc.stepkeeper.ui.ClientesActivity;
import com.evervc.stepkeeper.ui.EmpleadosActivity;
import com.evervc.stepkeeper.ui.functions.ClienteListener;
import com.evervc.stepkeeper.ui.functions.EmpleadoListener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmpleadoAdaptador extends RecyclerView.Adapter<EmpleadoAdaptador.EmpleadoViewHolder>{
    private List<Empleado> lstEmpleados;
    private Context context;
    private EmpleadoListener listener;
    private ActivityResultLauncher<Intent> activityLauncherEmpleados;
    public EmpleadoAdaptador(List<Empleado> lstEmpleados, Context context, EmpleadoListener listener, ActivityResultLauncher<Intent> activityLauncherEmpleados) {
        this.lstEmpleados = lstEmpleados;
        this.context = context;
        this.listener = listener;
        this.activityLauncherEmpleados = activityLauncherEmpleados;
    }
    @NonNull
    @Override
    public EmpleadoAdaptador.EmpleadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.empleado_item_lista, parent, false);
        return new EmpleadoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpleadoAdaptador.EmpleadoViewHolder holder, int position) {
        Empleado empleado = lstEmpleados.get(position);
        holder.imgItem.setImageResource(R.drawable.consumer);
        holder.tvNombreEmpleado.setText(empleado.getNombre());
        holder.tvPuestoEmpleado.setText(empleado.getPuesto());

        holder.btnEditarEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre el Activity de EmpleadosActivity y envía el id del empleado a editar
                Intent editarEmpleado = new Intent(context, EmpleadosActivity.class);
                editarEmpleado.putExtra("idEmpleadoAEditar", empleado.getId());
                activityLauncherEmpleados.launch(editarEmpleado);
            }
        });
        holder.btnEliminarEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mensajeDeConfirmacion = new AlertDialog.Builder(context);
                mensajeDeConfirmacion.setTitle("¿Está seguro que desea eliminar el registro?");
                mensajeDeConfirmacion.setMessage("Esta acción no puede revertirse, por lo tanto los cambios serán permanentes.");
                mensajeDeConfirmacion.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ExecutorService executorService = Executors.newSingleThreadExecutor();
                        executorService.execute(() -> {
                            ZapateriaDatabase db = ZapateriaDatabase.getInstancia(context);
                            int resultado = db.empleadoDao().eliminarEmpleado(empleado);
                            if (resultado > 0) {
                                listener.actualizarListaEmpleados();
                            }
                        });
                    }
                }).setNegativeButton("No", null).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstEmpleados.size();
    }

    public class EmpleadoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView tvNombreEmpleado, tvPuestoEmpleado;
        private ImageButton btnEditarEmpleado, btnEliminarEmpleado;
        public EmpleadoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            tvNombreEmpleado = itemView.findViewById(R.id.tvNombreEmpleado);
            tvPuestoEmpleado = itemView.findViewById(R.id.tvPuestoEmpleado);
            btnEditarEmpleado = itemView.findViewById(R.id.btnEditarEmpleado);
            btnEliminarEmpleado = itemView.findViewById(R.id.btnEliminarEmpleado);
        }
    }
}
