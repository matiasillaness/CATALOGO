package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.services;


import ar.edu.utn.frc.tup.lc.iv.common.services.ServiceCommon;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPut;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.entities.MarcaEntity;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.repositories.MarcaJpaRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceMarcaImpl implements ServiceMarca {
    @Autowired
    MarcaJpaRepository marcaJpaRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ServiceCommon serviceCommon;

    @Override
    public ResponseEntity<List<MarcaDto>> obtenerMarcas() {
        List<MarcaEntity> lste= marcaJpaRepository.findAll();
        List<MarcaDto> lst = new ArrayList<>();
        for (MarcaEntity e:lste){
            lst.add(modelMapper.map(e,MarcaDto.class));
        }
        return ResponseEntity.ok(lst);
    }

    @Override
    public ResponseEntity<MarcaDto> obtenerMarcaByCode(String code) {
        return ResponseEntity.ok(modelMapper.map(marcaJpaRepository.getByCodigo(code),MarcaDto.class));
    }

    @Override
    public ResponseEntity<MarcaDto> obtenerMarcaNombre(String nombre) {
        return ResponseEntity.ok(modelMapper.map(marcaJpaRepository.getByNombre(nombre),MarcaDto.class));
    }

    @Override
    public ResponseEntity<MarcaDto> crearMarca(MarcaDtoPost newMarca) {
        newMarca.setNombre(newMarca.getNombre().toUpperCase());
        if (marcaJpaRepository.existsByNombre(newMarca.getNombre())){
            throw new RuntimeException("Ya existe una marca con ese nombre.");
        }
        if (newMarca.getNombre().isEmpty()){
            throw new RuntimeException("El nombre de la marca no puede estar vacío.");
        }
        if (newMarca.getNombre().length() > 50) {
            throw new RuntimeException("El nombre de la marca no puede tener más de 50 caracteres.");
        }

        MarcaEntity marcaEntity = modelMapper.map(newMarca, MarcaEntity.class);

        if(serviceCommon.createCode(newMarca) != null){
            marcaEntity.setCodigo(serviceCommon.createCode(newMarca));
        }
        marcaJpaRepository.save(marcaEntity);
        return ResponseEntity.ok(modelMapper.map(marcaEntity,MarcaDto.class));

    }
    @Transactional
    @Override
    public ResponseEntity<Boolean> borrarMarca(String code) {
        marcaJpaRepository.deleteByCodigo(code);
        return ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<MarcaDto> modificarMarca(MarcaDtoPut newMarca, String code) {
        newMarca.setNombre(newMarca.getNombre().toUpperCase());
        MarcaEntity me= marcaJpaRepository.getByCodigo(code);

        if (marcaJpaRepository.existsByNombre(newMarca.getNombre())){
            throw new RuntimeException("Ya existe una marca con ese nombre.");
        }
        if (newMarca.getNombre().isEmpty()){
            throw new RuntimeException("El nombre de la marca no puede estar vacío.");
        }
        if (newMarca.getNombre().length() > 50) {
            throw new RuntimeException("El nombre de la marca no puede tener más de 50 caracteres.");
        }

        me.setNombre(newMarca.getNombre());
        MarcaEntity marcaSaved = marcaJpaRepository.save(me);
        return ResponseEntity.ok(modelMapper.map(marcaSaved, MarcaDto.class));
    }
}
