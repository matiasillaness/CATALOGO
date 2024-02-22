package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.controllers;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPut;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.services.ServiceMarca;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/brands")
public class MarcaController {
    @Autowired
    ServiceMarca serviceMarca;

    //TODO: Implementar los siguientes métodos de marcas
    @GetMapping("")
    public ResponseEntity<List<MarcaDto>> getAllMarcas() {
        return serviceMarca.obtenerMarcas() ;
    }

    @GetMapping("/{code}")
    public ResponseEntity<MarcaDto> getMarcaByCode(@PathVariable String code) {
        return serviceMarca.obtenerMarcaByCode(code);
    }

    @GetMapping("/name/{nombre}")
    public ResponseEntity<MarcaDto> getMarcaByNombre(@PathVariable String nombre) {
        return serviceMarca.obtenerMarcaNombre(nombre);
    }

    @PostMapping("") //todo: controlar antes que no exista una marca con el mismo nombre
    public ResponseEntity<MarcaDto> createMarca(@RequestBody MarcaDtoPost newMarca) {
        return serviceMarca.crearMarca(newMarca);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Boolean> eliminarMarca(@PathVariable String code) {
        return serviceMarca.borrarMarca(code);
    }

    @PutMapping("/{code}")
    public ResponseEntity<MarcaDto> modificarMarca(@PathVariable String code, @RequestBody MarcaDtoPut marca) {
        return serviceMarca.modificarMarca(marca, code);
    }
}
