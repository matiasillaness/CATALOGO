package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Descuento {

    String codigo;
    String nombre;
    String descripcion;
    private BigDecimal precio_min;
    private BigDecimal precio_top;
    String codigo_producto;
    boolean activo;
}
