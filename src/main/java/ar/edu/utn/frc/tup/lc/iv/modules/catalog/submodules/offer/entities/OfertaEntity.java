package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.entities;

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
public class OfertaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "oferta_id", length = 36)
    private String id;

    @Column(name = "codigo_oferta", length = 100)
    private String codigo; //3x2

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 1000)
    private String descripcion; //lleva 3 paga 2

    @Column(name = "activo", nullable = false)
    private boolean activo;

    @Column(name = "precio_oferta")
    private BigDecimal precio_oferta;

    @Column(name = "puntos")
    private BigDecimal puntos;

    //TODO: RELACIONES
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private ProductoEntity producto;

}
