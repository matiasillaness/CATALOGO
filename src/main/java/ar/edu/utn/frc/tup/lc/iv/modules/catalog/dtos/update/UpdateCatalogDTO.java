package ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCatalogDTO {
    String descripcion;
    String nombre;
    List<String> productos_asociados;
}

