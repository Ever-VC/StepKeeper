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
import com.evervc.stepkeeper.ci.ConvertidorTipoMovimiento;
import com.evervc.stepkeeper.database.ZapateriaDatabase;
import com.evervc.stepkeeper.enums.TipoMovimiento;
import com.evervc.stepkeeper.pojo.MovimientoInventarioPojo;
import com.evervc.stepkeeper.ui.functions.MovimientoListener;

import java.util.List;

public class MovimientoAdaptador extends RecyclerView.Adapter<MovimientoAdaptador.MovimientoViewHolder> {
    private List<MovimientoInventarioPojo> lstMovimientos;
    private Context context;
    private MovimientoListener listener;
    private ActivityResultLauncher<Intent> activityLauncherMovimientos;

    public MovimientoAdaptador(List<MovimientoInventarioPojo> lstMovimientos, Context context, MovimientoListener listener, ActivityResultLauncher<Intent> activityLauncherMovimientos) {
        this.lstMovimientos = lstMovimientos;
        this.context = context;
        this.listener = listener;
        this.activityLauncherMovimientos = activityLauncherMovimientos;
    }

    @NonNull
    @Override
    public MovimientoAdaptador.MovimientoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movimientos_item_lista, parent, false);
        return new MovimientoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovimientoAdaptador.MovimientoViewHolder holder, int position) {
        MovimientoInventarioPojo movimientoPojo = lstMovimientos.get(position);
        holder.imgItemMovimiento.setImageResource(R.drawable.shoes);
        holder.tvNombreProductoMovimiento.setText(movimientoPojo.producto.getNombre());
        ConvertidorTipoMovimiento convertirEnum = new ConvertidorTipoMovimiento();
        holder.tvTipoMovimiento.setText(
                "Tipo: " +
                        convertirEnum.convertirTipoMovimientoATexto(movimientoPojo.movimientoInventario.getTipo()) +
                        (movimientoPojo.movimientoInventario.getTipo() == TipoMovimiento.ENTRADA ? " +" : " -") +
                        movimientoPojo.movimientoInventario.getCantidad() +
                        "U"
        );
        //holder.tvTipoMovimiento.setText("Tipo: " + convertirEnum.convertirTipoMovimientoATexto(movimientoPojo.movimientoInventario.getTipo()) + "¿?" + movimientoPojo.movimientoInventario.getCantidad() + "U");
        holder.tvStock.setText("Stock: $0.00");

        holder.btnEditarMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.btnEliminarMovimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mensajeDeConfirmacion = new AlertDialog.Builder(context);
                mensajeDeConfirmacion.setTitle("¿Está seguro que desea eliminar el registro?");
                mensajeDeConfirmacion.setMessage("Esta acción no puede revertirse, por lo tanto los cambios serán permanentes.");
                mensajeDeConfirmacion.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*ZapateriaDatabase db = ZapateriaDatabase.getInstancia(context);
                        int resultado = db.movimientoInventarioDao().eliminarMovimientoInventario(movimientoPojo.movimientoInventario);
                        if (movimientoPojo.movimientoInventario.getTipo() == TipoMovimiento.SALIDA) {
                            // SE ELIMINAN TODO LOS ELEMENTOS ASOCIADOS CON EL DETALLE DE VENTA EN CASCADA
                            db.detalleVentaDao().eliminarDetallePorIdPorudcto(movimientoPojo.producto.getId());
                        } else {

                        }*/
                    }
                }).setNegativeButton("No", null).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstMovimientos.size();
    }

    public class MovimientoViewHolder extends RecyclerView.ViewHolder {
        ImageView imgItemMovimiento;
        TextView tvNombreProductoMovimiento, tvTipoMovimiento, tvStock;
        ImageButton btnEditarMovimiento, btnEliminarMovimiento;
        public MovimientoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgItemMovimiento = itemView.findViewById(R.id.imgItemMovimiento);
            tvNombreProductoMovimiento = itemView.findViewById(R.id.tvNombreProductoMovimiento);
            tvTipoMovimiento = itemView.findViewById(R.id.tvTipoMovimiento);
            tvStock = itemView.findViewById(R.id.tvStock);
            btnEditarMovimiento = itemView.findViewById(R.id.btnEditarMovimiento);
            btnEliminarMovimiento = itemView.findViewById(R.id.btnEliminarMovimiento);
        }
    }
}
