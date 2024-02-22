package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTOPut {
    String nombre;
    String descripcion;
    private BigDecimal precio_oferta;
    boolean activo;

    String codigo_producto;
}
