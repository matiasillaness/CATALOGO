package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.dtos;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTOOferta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OfferDTOProducto {
    String codigo;
    String nombre;
    String descripcion;
    private BigDecimal precio_oferta;
    BigDecimal puntos;
    boolean activo;

    ProductoDTOOferta producto;
}
