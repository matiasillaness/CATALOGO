package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPut;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.entities.CategoriaEntity;
import ar.edu.utn.frc.tup.lc.iv.common.services.ServiceCommon;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.repositories.CategoriaJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceCategoria implements iServiceCategoria {

    @Autowired
    CategoriaJpaRepository categoriaJpaRepository;
    @Autowired
    ServiceCommon serviceCommon;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<CategoriaDto>> getAllCategorias() {
        List<CategoriaEntity> lista = categoriaJpaRepository.findAll();

        List<CategoriaDto> listaDto = new ArrayList<>();
        for (CategoriaEntity e : lista) {
            listaDto.add(modelMapper.map(e, CategoriaDto.class));
        }
        return ResponseEntity.ok(listaDto);
    }

    @Override
    public ResponseEntity<CategoriaDto> getCategoriaByCodigo(String codigo) {
        CategoriaEntity categoriaEntity = categoriaJpaRepository.getByCodigo(codigo);

        if (categoriaEntity == null) {
            //throw new RuntimeException("No se encontró una categoría con el código especificado.");
            throw new EntityNotFoundException("No se encontró una categoría con el código especificado.");
        }

        return ResponseEntity.ok(modelMapper.map(categoriaEntity, CategoriaDto.class));
    }

    @Override
    public ResponseEntity<CategoriaDto> getCategoriaByNombre(String nombre) {
        CategoriaEntity categoriaEntity = categoriaJpaRepository.getByNombre(nombre);

        if (categoriaEntity == null) {
            //throw new RuntimeException("No se encontró una categoría con el nombre especificado.");
            throw new EntityNotFoundException("No se encontró una categoría con el nombre especificado.");
        }

        return ResponseEntity.ok(modelMapper.map(categoriaEntity, CategoriaDto.class));
    }

    @Transactional
    @Override
    public ResponseEntity<CategoriaDto> postCategoria(CategoriaDtoPost newCategoria) {
        newCategoria.setNombre(newCategoria.getNombre().toUpperCase());
        if (categoriaJpaRepository.getByNombre(newCategoria.getNombre()) != null) {
            throw new RuntimeException("Ya existe una categoría con ese nombre.");
        }
        if (newCategoria.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre de la categoría no puede estar vacío.");
        }
        if (newCategoria.getNombre().length() > 50) {
            throw new RuntimeException("El nombre de la categoría no puede tener más de 50 caracteres.");
        } else {
            CategoriaEntity categoriaEntity = modelMapper.map(newCategoria, CategoriaEntity.class);
            if (serviceCommon.createCode(newCategoria) != null) {
                categoriaEntity.setCodigo(serviceCommon.createCode(newCategoria));
            }
            categoriaJpaRepository.save(categoriaEntity);
            return ResponseEntity.ok(modelMapper.map(categoriaEntity, CategoriaDto.class));
        }
    }

    @Transactional
    @Override
    public ResponseEntity<Boolean> deleteCategoria(String code) {
        categoriaJpaRepository.deleteByCodigo(code);
        return ResponseEntity.ok(true);
    }

    /*@Transactional
    @Override
    public ResponseEntity<List<CategoriaDto>> deleteCategoriaByCodigo(String codigo) {
        List<CategoriaEntity> lista = categoriaJpaRepository.deleteByCodigo(codigo);

        if (lista.isEmpty()) {
            throw new RuntimeException("No se encontró una categoría con el código especificado.");
        }

        List<CategoriaDto> listaDto = new ArrayList<>();
        for (CategoriaEntity e : lista) {
            listaDto.add(modelMapper.map(e, CategoriaDto.class));
        }
        return ResponseEntity.ok(listaDto);
    }*/

    @Transactional
    @Override
    public ResponseEntity<CategoriaDto> putCategoria(CategoriaDtoPut newCategoria, String codigo) {
        CategoriaEntity categoriaEntity = categoriaJpaRepository.getByCodigo(codigo);
        newCategoria.setNombre(newCategoria.getNombre().toUpperCase());

        if (categoriaEntity == null) {
            //throw new RuntimeException("No se encontró una categoría con el código especificado.");
            throw new EntityNotFoundException("No se encontró una categoría con el código especificado.");
        }

        String nuevoNombre = newCategoria.getNombre();

        CategoriaEntity categoriaByNuevoNombre = categoriaJpaRepository.getByNombre(nuevoNombre);

        if (categoriaByNuevoNombre != null) {
            //throw new RuntimeException("Ya existe una categoría con ese nombre.");
            throw new EntityNotFoundException("Ya existe una categoría con ese nombre.");
        }
        if (nuevoNombre.isEmpty()) {
            //throw new RuntimeException("El nombre de la categoría no puede estar vacío.");
            throw new EntityNotFoundException("El nombre de la categoría no puede estar vacío.");
        }
        if (nuevoNombre.length() > 50) {
            //throw new RuntimeException("El nombre de la categoría no puede tener más de 50 caracteres.");
            throw new EntityNotFoundException("El nombre de la categoría no puede tener más de 50 caracteres.");
        }

        categoriaEntity.setNombre(nuevoNombre);

        CategoriaEntity categoriaSaved = categoriaJpaRepository.save(categoriaEntity);
        return ResponseEntity.ok(modelMapper.map(categoriaSaved, CategoriaDto.class));
    }
}
