package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceDiscount {
    ResponseEntity<List<DiscountDTOProducto>> getAllDescuentos();
    ResponseEntity<DiscountDTOProducto> getDescuentoByCodigo(String codigo);
    ResponseEntity<DiscountDTOProducto> getDescuentoByNombre(String nombre);
    ResponseEntity<List<DiscountDTO>> getAllDescuentosByProductCode(String code);
    ResponseEntity<DiscountDTO> postDescuento(DiscountDTOPost newCategoria);
    ResponseEntity<DiscountDTODelete> deleteDescuento(String code);
    ResponseEntity<DiscountDTOPut> putDescuento(DiscountDTOPut newDescuento, String code);
}
