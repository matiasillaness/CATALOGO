package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTODescuento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTOProducto {
    String codigo;
    String nombre;
    String descripcion;
    private BigDecimal precio_min;
    boolean activo;
    ProductoDTODescuento producto;
}
