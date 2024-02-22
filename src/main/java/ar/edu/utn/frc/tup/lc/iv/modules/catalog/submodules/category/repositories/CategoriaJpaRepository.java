package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.repositories;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.entities.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoriaJpaRepository extends JpaRepository<CategoriaEntity, String> {
    CategoriaEntity getReferenceById(String id);
    CategoriaEntity getByCodigo(String codigo);
    CategoriaEntity getByNombre(String nombre);
    List<CategoriaEntity> deleteByCodigo(String codigo);
    CategoriaEntity getByCodigoLike(String nombreTruncado);
    boolean existsByCodigo(String code);
}
