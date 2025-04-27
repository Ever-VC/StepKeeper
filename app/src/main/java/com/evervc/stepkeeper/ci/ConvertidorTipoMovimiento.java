package com.evervc.stepkeeper.ci;

import androidx.room.TypeConverter;

import com.evervc.stepkeeper.enums.TipoMovimiento;

public class ConvertidorTipoMovimiento {

    // Método para convertir de ENUM a String
    @TypeConverter
    public String convertirTipoMovimientoATexto(TipoMovimiento tipoMovimiento) {
        if (tipoMovimiento == null) {
            return null;
        }
        return tipoMovimiento.name(); // Convierte a String (nombre del ENUM)
    }

    // Método para convertir de String a ENUM
    @TypeConverter
    public TipoMovimiento convertirTextoATipoMovimiento(String texto) {
        if (texto == null) {
            return null;
        }
        return TipoMovimiento.valueOf(texto); // Convierte el String al ENUM
    }
}
