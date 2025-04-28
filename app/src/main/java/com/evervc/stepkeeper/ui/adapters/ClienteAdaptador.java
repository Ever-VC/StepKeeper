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
import com.evervc.stepkeeper.ui.ClientesActivity;
import com.evervc.stepkeeper.ui.functions.ClienteListener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClienteAdaptador extends RecyclerView.Adapter<ClienteAdaptador.ClienteViewHolder> {
    private List<Cliente> lstClientes;
    private Context context;
    private ClienteListener listener;
    private ActivityResultLauncher<Intent> activityLauncherClientes;

    public ClienteAdaptador(List<Cliente> lstClientes, Context context, ClienteListener listener, ActivityResultLauncher<Intent> activityLauncherClientes) {
        this.lstClientes = lstClientes;
        this.context = context;
        this.listener = listener;
        this.activityLauncherClientes = activityLauncherClientes;
    }

    @NonNull
    @Override
    public ClienteAdaptador.ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cliente_item_lista, parent, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteAdaptador.ClienteViewHolder holder, int position) {
        Cliente cliente = lstClientes.get(position);
        holder.imgItem.setImageResource(R.drawable.consumer);
        holder.tvNombreCliente.setText(cliente.getNombre());
        holder.tvCorreoCliente.setText(cliente.getCorreo());

        holder.btnEditarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre el Activity de ClientesActivity y envía el id del cliente a editar
                Intent editarCliente = new Intent(context, ClientesActivity.class);
                editarCliente.putExtra("idClienteAEditar", cliente.getId());
                activityLauncherClientes.launch(editarCliente);
            }
        });

        holder.btnEliminarCliente.setOnClickListener(new View.OnClickListener() {
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
                            int resultado = db.clienteDao().eliminarCliente(cliente);
                            if (resultado > 0) {
                                listener.actualizarListaClientes();
                            }
                        });
                    }
                }).setNegativeButton("No", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstClientes.size();
    }

    public class ClienteViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgItem;
        private TextView tvNombreCliente, tvCorreoCliente;
        private ImageButton btnEditarCliente, btnEliminarCliente;
        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            tvNombreCliente = itemView.findViewById(R.id.tvNombreCliente);
            tvCorreoCliente = itemView.findViewById(R.id.tvCorreoCliente);
            btnEditarCliente = itemView.findViewById(R.id.btnEditarCliente);
            btnEliminarCliente = itemView.findViewById(R.id.btnEliminarCliente);
        }
    }
}
