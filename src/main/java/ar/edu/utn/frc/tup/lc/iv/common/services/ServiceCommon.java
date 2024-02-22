package ar.edu.utn.frc.tup.lc.iv.common.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos.DiscountDTOSimple;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.dtos.OfferDTOSimple;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.entities.ProductoEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceCommon {


    String createCode(Object clase);
    void mapearCategoriasToProducts(ProductoDTO prodMapped, List<CategoriaDto> catMapped, ProductoEntity productoEntity);
    List<DiscountDTOSimple> getAllDescuentosByProductCode(String code);
    List<OfferDTOSimple> getAllOfertasByProductCode(String code);
}
