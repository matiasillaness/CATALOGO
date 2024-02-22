package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "categoria_id")
    private String id;

    @Column(name = "codigo_categoria", length = 100)
    private String codigo; //3x2

    @Column
    String nombre;

}
