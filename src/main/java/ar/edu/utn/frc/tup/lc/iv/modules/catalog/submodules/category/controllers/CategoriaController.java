package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.controllers;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPut;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.services.iServiceCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/categories")
public class CategoriaController {

    @Autowired
    iServiceCategoria serviceCategoria;

    @GetMapping("")
    public ResponseEntity<List<CategoriaDto>> getAllCategorias() {
        return serviceCategoria.getAllCategorias();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<CategoriaDto> getCategoriaByCodigo(@PathVariable String codigo) {
        codigo = codigo.toUpperCase();
        return serviceCategoria.getCategoriaByCodigo(codigo);
    }

    @GetMapping("/name/{nombre}")
    public ResponseEntity<CategoriaDto> getCategoriaByNombre(@PathVariable String nombre) {
        return serviceCategoria.getCategoriaByNombre(nombre);
    }

    @PostMapping("")
    public ResponseEntity<CategoriaDto> createCategoria(@RequestBody CategoriaDtoPost newCategoria) {
        return serviceCategoria.postCategoria(newCategoria);
    }

    /*@DeleteMapping("/eliminarCategoria/{id}")
    public ResponseEntity<Boolean> eliminarCategoria(@PathVariable String id) {
        return serviceCategoria.deleteCategoria(id);
    }*/

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Boolean> eliminarCategoriasPorCodigo(@PathVariable String codigo) {
        codigo = codigo.toUpperCase();
        return serviceCategoria.deleteCategoria(codigo);
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<CategoriaDto> putCategoria(@PathVariable String codigo, @RequestBody CategoriaDtoPut categoria) {
        codigo = codigo.toUpperCase();
        return serviceCategoria.putCategoria(categoria, codigo);
    }
}
