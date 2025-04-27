package com.evervc.stepkeeper.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evervc.stepkeeper.R;
import com.evervc.stepkeeper.models.Cliente;
import com.evervc.stepkeeper.ui.functions.ClienteListener;

import java.util.List;

public class ClienteAdaptador extends RecyclerView.Adapter<ClienteAdaptador.ClienteViewHolder> {
    private List<Cliente> lstClientes;
    private Context context;
    private ClienteListener listener;

    public ClienteAdaptador(List<Cliente> lstClientes, Context context, ClienteListener listener) {
        this.lstClientes = lstClientes;
        this.context = context;
        this.listener = listener;
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

            }
        });

        holder.btnEliminarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.actualizarListaClientes();
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
