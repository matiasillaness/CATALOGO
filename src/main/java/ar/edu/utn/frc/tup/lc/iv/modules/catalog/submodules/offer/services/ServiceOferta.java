package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.dtos.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceOferta {
    ResponseEntity<List<OfferDTOProducto>> getAllOfertas();
    ResponseEntity<OfferDTOProducto> getOfertaByCodigo(String codigo);
    ResponseEntity<OfferDTOProducto> getOfertaByNombre(String nombre);
    ResponseEntity<List<OfferDTO>> getAllOfertasByProductCode(String code);
    ResponseEntity<OfferDTO> postOferta(OfferDTOPost newOferta);
    ResponseEntity<OfferDTODelete> deleteOferta(String code);
    ResponseEntity<OfferDTOPut> putOferta(OfferDTOPut newOferta, String code);
}
