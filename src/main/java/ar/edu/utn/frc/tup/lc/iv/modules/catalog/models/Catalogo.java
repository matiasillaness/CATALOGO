package ar.edu.utn.frc.tup.lc.iv.modules.catalog.models;

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
public class Catalogo {
    @NotBlank(message = "El campo 'Code' no debe estar vacío.")
    String codigo_catalogo;
    @Nonnull
    @NotBlank
    String descripcion;
    @NonNull
    @NotBlank
    String nombre;
    @Nonnull
    List<String> productos_asociados;
}
