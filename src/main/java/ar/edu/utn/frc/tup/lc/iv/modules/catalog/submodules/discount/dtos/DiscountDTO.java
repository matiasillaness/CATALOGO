package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTO {
    String codigo;
    String nombre;
    String descripcion;
    private BigDecimal precio_min;
    boolean activo;
    String codigo_producto;
}
