package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServiceMarca {
    ResponseEntity<List<MarcaDto>> obtenerMarcas();
    ResponseEntity<MarcaDto> obtenerMarcaByCode(String code);
    ResponseEntity<MarcaDto> obtenerMarcaNombre(String nombre);
    ResponseEntity<MarcaDto> crearMarca(MarcaDtoPost newMarca);
    ResponseEntity<Boolean> borrarMarca(String code);
    ResponseEntity<MarcaDto> modificarMarca(MarcaDtoPut newMarca, String code);

}
