package ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.create;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogCreateDTO {
    @NonNull
    @NotBlank
    String nombre;

    @Nonnull
    @NotBlank
    String descripcion;

    @Nonnull
    List<String> productos_asociados;
}

