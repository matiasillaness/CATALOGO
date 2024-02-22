package ar.edu.utn.frc.tup.lc.iv.modules.catalog.repositories;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.entities.CatalogoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatalogJpaRepository  extends JpaRepository<CatalogoEntity, String>{
    CatalogoEntity getByCodigo(String codigo);
    CatalogoEntity getByNombre(String name);
    void deleteByCodigo(String codigo);

    boolean existsByCodigo(String code);
}
