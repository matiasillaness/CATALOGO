package ar.edu.utn.frc.tup.lc.iv.modules.catalog.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.common.CatalogResponseDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.create.CatalogCreateDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.update.UpdateCatalogDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IServiceCatalog {
    ResponseEntity<CatalogResponseDTO> postCatalog(CatalogCreateDTO newCatalogo);
    ResponseEntity<List<CatalogResponseDTO>> getAllCatalogs();
    ResponseEntity<CatalogResponseDTO> getCatalogByCode(String code);
    ResponseEntity<Boolean> deleteCatalogByCode(String code);
    ResponseEntity<CatalogResponseDTO> updateCatalogByCode(String code, UpdateCatalogDTO payload);
}
