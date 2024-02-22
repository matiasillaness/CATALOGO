package ar.edu.utn.frc.tup.lc.iv.modules.catalog.controllers;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.create.CatalogCreateDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.common.CatalogResponseDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.update.UpdateCatalogDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.services.IServiceCatalog;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/catalogs")
public class CatalogController {

    @Autowired
    IServiceCatalog serviceCatalog;

    @PostMapping("")
    public ResponseEntity<CatalogResponseDTO> createCatalog(@RequestBody @Valid CatalogCreateDTO newCatalog) {
        return serviceCatalog.postCatalog(newCatalog);
    }
    @PutMapping ("/{code}")
    public ResponseEntity<CatalogResponseDTO> updateCatalog(@PathVariable String code, @RequestBody @Valid UpdateCatalogDTO payload) {
        code = code.toUpperCase();
        return serviceCatalog.updateCatalogByCode(code, payload);
    }
    @GetMapping("")
    public ResponseEntity<List<CatalogResponseDTO>> getAllCatalogs() {
        return serviceCatalog.getAllCatalogs();
    }
    @DeleteMapping("/{code}")
    public ResponseEntity<Boolean>  deleteCatalogByCode(@PathVariable String code){
        code = code.toUpperCase();
        return serviceCatalog.deleteCatalogByCode(code);
    }
    @GetMapping("/{code}")
    public ResponseEntity<CatalogResponseDTO>  getCatalogByCode(@PathVariable String code){
        code = code.toUpperCase();
        return serviceCatalog.getCatalogByCode(code);
    }
}
