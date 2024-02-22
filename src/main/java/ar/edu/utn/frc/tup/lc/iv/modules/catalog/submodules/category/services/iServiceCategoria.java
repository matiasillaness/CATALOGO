package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface iServiceCategoria {
    ResponseEntity<List<CategoriaDto>> getAllCategorias();
    ResponseEntity<CategoriaDto> getCategoriaByCodigo(String codigo);
    ResponseEntity<CategoriaDto> getCategoriaByNombre(String nombre);
    ResponseEntity<CategoriaDto> postCategoria(CategoriaDtoPost newCategoria);
    ResponseEntity<Boolean> deleteCategoria(String id);
    //ResponseEntity<List<CategoriaDto>> deleteCategoriaByCodigo(String codigo);
    ResponseEntity<CategoriaDto> putCategoria(CategoriaDtoPut newCategoria, String codigo);
}
