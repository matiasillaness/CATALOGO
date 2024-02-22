package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.entities;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.entities.ProductoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DescuentoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "descuento_id")
    private String id;

    @Column(name = "codigo_descuento", length = 100)
    private String codigo; //3x2

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @Column(name = "precio_min")
    private BigDecimal precio_min;

    // RELATIONSHIPS

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private ProductoEntity producto;
}