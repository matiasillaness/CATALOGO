package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.repositories;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.entities.MarcaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaJpaRepository extends JpaRepository<MarcaEntity, String> {
    MarcaEntity getByNombre(String nombre);

    MarcaEntity getByCodigo(String codigo);
    MarcaEntity getReferenceById(String id);
    List<MarcaEntity> deleteByCodigo(String code);

    boolean existsByCodigo(String codigo);

    boolean existsByNombre(String nombre);
}
