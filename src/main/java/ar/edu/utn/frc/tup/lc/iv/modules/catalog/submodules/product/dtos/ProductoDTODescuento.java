package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoDTODescuento {
    String codigo;
    String nombre;
    String descripcion;
    String precio_compra;
    String precio_mayorista;
    String precio_minorista;
    String imageURL;
    String dimensiones;
    String peso;
    String material;
    String color;
    String paisOrigen;
    Boolean activo;
}
