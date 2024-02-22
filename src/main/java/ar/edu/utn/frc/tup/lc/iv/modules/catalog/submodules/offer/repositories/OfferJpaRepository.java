package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.repositories;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.entities.OfertaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferJpaRepository extends JpaRepository<OfertaEntity, String> {
    OfertaEntity getByCodigo(String codigo);
    OfertaEntity getByNombre(String nombre);
    List<OfertaEntity> getAllByProducto_Codigo(String codigo);

    boolean existsByCodigo(String code);
}
