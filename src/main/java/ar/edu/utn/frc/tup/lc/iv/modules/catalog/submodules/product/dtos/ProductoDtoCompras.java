package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@NoArgsConstructor
@Data
public class ProductoDtoCompras {

    private String nombre;
    private String codigo;
    private BigDecimal precio_compra;
    private boolean exito;
    private String mensaje;
    // Constructores
    public ProductoDtoCompras(String id, boolean exito, String mensaje, String nombre, BigDecimal precio_compra) {
        this.codigo = id;
        this.exito = exito;
        this.mensaje = mensaje;
        this.precio_compra = precio_compra;
        this.nombre = nombre;
    }




}
