package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.controllers;


import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos.*;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.services.ServiceDiscount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/discounts")
public class DiscountController {
    @Autowired
    ServiceDiscount serviceDiscount;

    //TODO: Implementar los siguientes métodos de descuentos

    @GetMapping("")
    public ResponseEntity<List<DiscountDTOProducto>> getAllDescuentos() {
        return serviceDiscount.getAllDescuentos();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<DiscountDTOProducto> getDescuentoByCodigo(@PathVariable String codigo) {
        return serviceDiscount.getDescuentoByCodigo(codigo);
    }

    @GetMapping("/name/{nombre}")
    public ResponseEntity<DiscountDTOProducto> getDescuentoByNombre(@PathVariable String nombre) {
        return serviceDiscount.getDescuentoByNombre(nombre);
    }
    @GetMapping("/product/{codigo}")
    public ResponseEntity<List<DiscountDTO>> getAllDescuentosByProduct(@PathVariable String codigo) {
        return serviceDiscount.getAllDescuentosByProductCode(codigo);
    }

    @PostMapping("") //todo: controlar antes que no exista una categoria con el mismo nombre
    public ResponseEntity<DiscountDTO> createDescuento(@RequestBody DiscountDTOPost newDescuento) {
        return serviceDiscount.postDescuento(newDescuento);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<DiscountDTODelete> eliminarDescuentoByCode(@PathVariable String codigo) {
        return serviceDiscount.deleteDescuento(codigo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<DiscountDTOPut> modificarDescuento(@PathVariable String codigo, @RequestBody DiscountDTOPut descuento) {
        return serviceDiscount.putDescuento(descuento, codigo);
    }
}
