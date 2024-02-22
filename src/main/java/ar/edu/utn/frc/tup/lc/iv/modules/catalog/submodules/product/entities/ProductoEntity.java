package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.entities;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.entities.MarcaEntity;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.entities.CategoriaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
@Entity
@Table(name = "productos")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "producto_id", nullable = false)
    private String id;

    @Column(name = "codigo", length = 200)
    private String codigo;

    @Column(name = "nombre", length = 200)
    private String nombre;

    @Column(name = "producto_descripcion", length = 1000)
    private String descripcion;

    @Column(name = "activo")
    private Boolean activo = false;

    @Column(name = "precio_compra")
    private BigDecimal precio_compra;

    @Column(name = "precio_minorista")
    private BigDecimal precio_minorista;

    @Column(name = "precio_mayorista")
    private BigDecimal precio_mayorista;

    @Column(name = "dimensiones")
    private String dimensiones;

    @Column(name = "peso")
    private String peso;

    @Column(name = "material")
    private String material;

    @Column(name = "color")
    private String color;
  
    @Column(name = "paisOrigen", length = 200)
    private String paisOrigen;


    @Column(name="imageUrl")
    private String imageUrl;

    //todo: RELATION
  
    //TODO: RELACION CON MARCA
    @ManyToOne
    @JoinColumn(name = "marca_id")
    private MarcaEntity marcaEntity;


    //TODO: RELACION CON CATEGORIA
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "producto_categoria",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<CategoriaEntity> categoriaEntities;



}
