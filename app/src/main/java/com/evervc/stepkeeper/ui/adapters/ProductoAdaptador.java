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
import com.evervc.stepkeeper.pojo.ProductoPojo;
import com.evervc.stepkeeper.ui.ProductosActivity;
import com.evervc.stepkeeper.ui.functions.ProductoListener;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductoAdaptador extends RecyclerView.Adapter<ProductoAdaptador.ProductoViewHolder> {
    private List<ProductoPojo> lstProductos;
    private Context context;
    private ProductoListener listener;
    private ActivityResultLauncher<Intent> activityLauncherProductos;

    public ProductoAdaptador(List<ProductoPojo> lstProductos, Context context, ProductoListener listener, ActivityResultLauncher<Intent> activityLauncherProductos) {
        this.lstProductos = lstProductos;
        this.context = context;
        this.listener = listener;
        this.activityLauncherProductos = activityLauncherProductos;
    }

    @NonNull
    @Override
    public ProductoAdaptador.ProductoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.producto_item_lista, parent, false);
        return new ProductoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdaptador.ProductoViewHolder holder, int position) {
        ProductoPojo productoPojo = lstProductos.get(position);
        holder.imgItemProducto.setImageResource(R.drawable.shoes);
        holder.tvNombreProducto.setText(productoPojo.producto.getNombre());
        holder.tvCategoriaProducto.setText(productoPojo.categoria.getNombre());
        holder.tvMarcaProducto.setText(productoPojo.marca.getNombre());

        holder.btnEditarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Abre el activity de editar y manda el id del producto a editar
                Intent actualizarProducto = new Intent(context, ProductosActivity.class);
                actualizarProducto.putExtra("idProductoAEditar", productoPojo.producto.getId());
                activityLauncherProductos.launch(actualizarProducto);
            }
        });

        holder.btnEliminarProducto.setOnClickListener(new View.OnClickListener() {
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
                            int resultado = db.productoDao().eliminarProducto(productoPojo.producto);
                            if (resultado > 0) {
                                listener.actualizarListaProductos();
                            }
                        });
                    }
                }).setNegativeButton("No", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstProductos.size();
    }

    public class ProductoViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgItemProducto;
        private TextView tvNombreProducto, tvCategoriaProducto, tvMarcaProducto;
        private ImageButton btnEditarProducto, btnEliminarProducto;
        public ProductoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemProducto = itemView.findViewById(R.id.imgItemProducto);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvCategoriaProducto = itemView.findViewById(R.id.tvCategoriaProducto);
            tvMarcaProducto = itemView.findViewById(R.id.tvMarcaProducto);
            btnEditarProducto = itemView.findViewById(R.id.btnEditarProducto);
            btnEliminarProducto = itemView.findViewById(R.id.btnEliminarProducto);
        }
    }
}
