package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountDTODelete {
    boolean status;
    String message;
}
