package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@NotNull
public class ProductoPreCarga {

    @Size(min = 1, max = 100, message = "the name must be between 1 and 100 characters")
    @NotNull(message = "the name cannot be null")
    String nombre;


    @NotNull(message = "The price cannot be null")
    @Min(value = 0, message = "The price must be greater than 0")
    @Max(value = 999999999, message = "the price must be less than 999999999")
    BigDecimal precio_compra;
}
