package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.models.Marca;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos.DiscountDTOSimple;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.dtos.OfferDTOSimple;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductoDTO {
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
    Marca marca;
    List<CategoriaDto> categorias;
    List<OfferDTOSimple> ofertas;
    List<DiscountDTOSimple> descuentos;
    Boolean activo;



}
