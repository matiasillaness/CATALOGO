package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.repositories;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.entities.DescuentoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountJpaRepository extends JpaRepository<DescuentoEntity, String> {
    DescuentoEntity getByCodigo(String codigo);
    DescuentoEntity getByNombre(String nombre);
    List<DescuentoEntity> deleteByCodigo(String codigo);
    List<DescuentoEntity> getAllByProducto_Codigo(String codigo);
    boolean existsByCodigo(String code);
}
