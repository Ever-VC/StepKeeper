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
import com.evervc.stepkeeper.models.Categoria;
import com.evervc.stepkeeper.models.Marca;
import com.evervc.stepkeeper.ui.CategoriasActivity;
import com.evervc.stepkeeper.ui.MarcasActivity;
import com.evervc.stepkeeper.ui.functions.CategoriaListener;
import com.evervc.stepkeeper.ui.functions.MarcaListener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MarcaAdaptador extends RecyclerView.Adapter<MarcaAdaptador.MarcaViewHolder>{
    private List<Marca> lstMarcas;
    private Context context;
    private MarcaListener listener;
    private ActivityResultLauncher<Intent> activityLauncherMarcas;

    public MarcaAdaptador(List<Marca> lstMarcas, Context context, MarcaListener listener, ActivityResultLauncher<Intent> activityLauncherMarcas) {
        this.lstMarcas = lstMarcas;
        this.context = context;
        this.listener = listener;
        this.activityLauncherMarcas = activityLauncherMarcas;
    }
    @NonNull
    @Override
    public MarcaAdaptador.MarcaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.marca_item_lista, parent, false);
        return new MarcaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MarcaAdaptador.MarcaViewHolder holder, int position) {
        Marca marca = lstMarcas.get(position);
        holder.imgItem.setImageResource(R.drawable.consumer);
        holder.tvNombreMarca.setText(marca.getNombre());

        holder.btnEditarMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre el Activity de MarcasActivity y envía el id del marca a editar
                Intent editarMarca = new Intent(context, MarcasActivity.class);
                editarMarca.putExtra("idMarcaAEditar", marca.getId());
                activityLauncherMarcas.launch(editarMarca);
            }
        });

        holder.btnEliminarMarca.setOnClickListener(new View.OnClickListener() {
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
                            int resultado = db.marcaDao().eliminarMarca(marca);
                            if (resultado > 0) {
                                listener.actualizarListaMarcas();
                            }
                        });
                    }
                }).setNegativeButton("No", null).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstMarcas.size();
    }

    public class MarcaViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView tvNombreMarca;
        private ImageButton btnEditarMarca, btnEliminarMarca;
        public MarcaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            tvNombreMarca = itemView.findViewById(R.id.tvNombreMarca);
            btnEditarMarca = itemView.findViewById(R.id.btnEditarMarca);
            btnEliminarMarca = itemView.findViewById(R.id.btnEliminarMarca);
        }
    }
}
