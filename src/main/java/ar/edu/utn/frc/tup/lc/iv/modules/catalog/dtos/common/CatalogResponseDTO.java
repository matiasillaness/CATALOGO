package ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.common;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogResponseDTO {
        String codigo;
        String nombre;
        String descripcion;
        List<ProductoDTO> productos_asociados;
}
