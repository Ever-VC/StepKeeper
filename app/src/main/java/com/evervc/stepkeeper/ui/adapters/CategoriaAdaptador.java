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
import com.evervc.stepkeeper.ui.CategoriasActivity;
import com.evervc.stepkeeper.ui.functions.CategoriaListener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CategoriaAdaptador extends RecyclerView.Adapter<CategoriaAdaptador.CategoriaViewHolder>{
    private List<Categoria> lstCategorias;
    private Context context;
    private CategoriaListener listener;
    private ActivityResultLauncher<Intent> activityLauncherCategorias;

    public CategoriaAdaptador(List<Categoria> lstCategorias, Context context, CategoriaListener listener, ActivityResultLauncher<Intent> activityLauncherCategorias) {
        this.lstCategorias = lstCategorias;
        this.context = context;
        this.listener = listener;
        this.activityLauncherCategorias = activityLauncherCategorias;
    }

    @NonNull
    @Override
    public CategoriaAdaptador.CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.categoria_item_lista, parent, false);
        return new CategoriaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriaAdaptador.CategoriaViewHolder holder, int position) {
        Categoria categoria = lstCategorias.get(position);
        holder.imgItem.setImageResource(R.drawable.consumer);
        holder.tvNombreCategoria.setText(categoria.getNombre());

        holder.btnEditarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Abre el Activity de CategoriasActivity y envía el id del categoria a editar
                Intent editarCategoria = new Intent(context, CategoriasActivity.class);
                editarCategoria.putExtra("idCategoriaAEditar", categoria.getId());
                activityLauncherCategorias.launch(editarCategoria);
            }
        });

        holder.btnEliminarCategoria.setOnClickListener(new View.OnClickListener() {
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
                            int resultado = db.categoriaDao().eliminarCategoria(categoria);
                            if (resultado > 0) {
                                listener.actualizarListaCategorias();
                            }
                        });
                    }
                }).setNegativeButton("No", null).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstCategorias.size();
    }

    public class CategoriaViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItem;
        private TextView tvNombreCategoria;
        private ImageButton btnEditarCategoria, btnEliminarCategoria;
        public CategoriaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItem = itemView.findViewById(R.id.imgItem);
            tvNombreCategoria = itemView.findViewById(R.id.tvNombreCategoria);
            btnEditarCategoria = itemView.findViewById(R.id.btnEditarCategoria);
            btnEliminarCategoria = itemView.findViewById(R.id.btnEliminarCategoria);
        }
    }
}
