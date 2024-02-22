package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.repositories;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoJpaRepository extends JpaRepository<ProductoEntity, String> {



    ProductoEntity getByCodigo(String codigo);

    boolean existsByCodigo(String code);

    ProductoEntity getByNombre(String nombre);
}