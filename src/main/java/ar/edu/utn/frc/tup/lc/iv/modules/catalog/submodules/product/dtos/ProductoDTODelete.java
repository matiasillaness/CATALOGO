package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductoDTODelete {
    boolean status;
    String message;
}
