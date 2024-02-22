package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Oferta {
    String codigo;
    String nombre;
    String descripcion;
    private BigDecimal precio_oferta;
    BigDecimal puntos;
    boolean activo;

    String codigo_producto;
}
