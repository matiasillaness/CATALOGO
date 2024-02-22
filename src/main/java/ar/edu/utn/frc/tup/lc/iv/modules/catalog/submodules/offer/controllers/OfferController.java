package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.controllers;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.dtos.*;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.services.ServiceOferta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/offers")
public class OfferController {

    @Autowired
    ServiceOferta serviceOferta;

    //TODO: Implementar los siguientes métodos de ofertas

    @GetMapping("")
    public ResponseEntity<List<OfferDTOProducto>> getAllOfertas() {
        return serviceOferta.getAllOfertas();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<OfferDTOProducto> getOfertaByCodigo(@PathVariable String codigo) {
        return serviceOferta.getOfertaByCodigo(codigo);
    }

    @GetMapping("/name/{nombre}")
    public ResponseEntity<OfferDTOProducto> getOfertaByNombre(@PathVariable String nombre) {
        return serviceOferta.getOfertaByNombre(nombre);
    }

    @GetMapping("/product/{codigo}")
    public ResponseEntity<List<OfferDTO>> getAllOfertasByProduct(@PathVariable String codigo) {
        return serviceOferta.getAllOfertasByProductCode(codigo);
    }

    @PostMapping("") //todo: controlar antes que no exista una categoria con el mismo nombre
    public ResponseEntity<OfferDTO> createOferta(@RequestBody OfferDTOPost newOferta) {
        return serviceOferta.postOferta(newOferta);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<OfferDTODelete> eliminarOfertaByCode(@PathVariable String codigo) {
        return serviceOferta.deleteOferta(codigo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<OfferDTOPut> modificarOferta(@PathVariable String codigo, @RequestBody OfferDTOPut descuento) {
        return serviceOferta.putOferta(descuento, codigo);
    }
}
