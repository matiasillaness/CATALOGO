package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "marcas")
public class MarcaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "marca_id")
    String id;
    @Column
    String nombre;

    @Column(name = "codigo_marca", length = 100)
    String codigo; //3x2

}

