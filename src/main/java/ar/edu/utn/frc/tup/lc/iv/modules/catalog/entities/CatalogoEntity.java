package ar.edu.utn.frc.tup.lc.iv.modules.catalog.entities;

import java.util.List;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.entities.ProductoEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CatalogoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "codigo_catalogo", length = 100, unique = true)
    private String codigo; //3x2

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "catalogo_producto",
            joinColumns = @JoinColumn(name = "catalogo_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private List<ProductoEntity> productos_asociados;

    public void addProduct(ProductoEntity productoEntity) {
        this.productos_asociados.add(productoEntity);
    }
}
