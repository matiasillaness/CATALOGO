package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDTOPut {
    String nombre;
    String descripcion;
    String codigo_producto;
    boolean activo;
}
