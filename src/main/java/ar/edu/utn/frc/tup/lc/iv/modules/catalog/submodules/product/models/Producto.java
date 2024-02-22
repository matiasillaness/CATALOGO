package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Producto {

    @NotNull(message = "the descripcion cannot be null")
    String descripcion;

    @NotNull(message = "the price cannot be null")
            @Min(value = 0, message = "the price must be greater than 0")
            @Max(value = 999999999, message = "the price must be less than 999999999")
    BigDecimal precio_compra;


    String imageURL;


    String dimensiones;

    @NotNull(message = "the weight cannot be null")
    @Min(value = 0, message = "the weight must be greater than 0")
    @Max(value = 999999999, message = "the weight must be less than 999999999")
    String peso;


    String material;
    String color;
    String paisOrigen;

    @NotNull(message = "the code_brand cannot be null")
    String codigo_marca;


    List<String> codigo_categorias;


    boolean activo;
}




