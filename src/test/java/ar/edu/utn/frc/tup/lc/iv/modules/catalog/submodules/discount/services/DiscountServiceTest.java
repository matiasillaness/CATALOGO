package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.models.Marca;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.services.ServiceMarca;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.services.ServiceCategoria;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos.*;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDtoCompras;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.Producto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.ProductoPreCarga;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.services.ServiceProducto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class DiscountServiceTest {

    @Autowired
    private ServiceDiscount serviceDiscount;

    @Autowired
    private ServiceProducto serviceProducto;

    @Autowired
    private ServiceMarca serviceMarca;

    @Autowired
    private ServiceCategoria serviceCategoria;

    @Autowired
    ModelMapper modelMapper;

    private ProductoDTO productoDTO,productoDTO2,productoDTO3,productoDTO4;

    @BeforeEach
    public void setup() {

        // PRODUCTO 1
        ProductoPreCarga productoPreCarga1 = new ProductoPreCarga();
        productoPreCarga1.setNombre("Martillo de Hierro");
        productoPreCarga1.setPrecio_compra(new BigDecimal(800));
        ResponseEntity<ProductoDtoCompras> p1 = serviceProducto.createProducto(productoPreCarga1);

        modelMapper.map(serviceMarca.crearMarca(new MarcaDtoPost("IRON")).getBody(), Marca.class);

        serviceCategoria.postCategoria(new CategoriaDtoPost("HERRAMIENTAS")).getBody();
        serviceCategoria.postCategoria(new CategoriaDtoPost("CONSTRUCCION")).getBody();

        Producto producto1 = new Producto();
        producto1.setDescripcion("Martillo de Hierro resistente para trabajos de construcción");
        producto1.setPrecio_compra(new BigDecimal(800));
        producto1.setImageURL("https://example.com/martillo.jpg");
        producto1.setDimensiones("20x10x5 cm");
        producto1.setPeso("1 kg");
        producto1.setMaterial("Hierro");
        producto1.setColor("Negro");
        producto1.setPaisOrigen("España");
        producto1.setCodigo_marca("M-IRO-001");
        producto1.setCodigo_categorias(List.of("C-HER-001", "C-CON-001"));
        producto1.setActivo(true);

        productoDTO = serviceProducto.updateProductoInactivo(producto1, p1.getBody().getCodigo()).getBody();

        // PRODUCTO 2
        ProductoPreCarga productoPreCarga2 = new ProductoPreCarga();
        productoPreCarga2.setNombre("Destornillador Eléctrico");
        productoPreCarga2.setPrecio_compra(new BigDecimal(1200));
        ResponseEntity<ProductoDtoCompras> p2 = serviceProducto.createProducto(productoPreCarga2);

        modelMapper.map(serviceMarca.crearMarca(new MarcaDtoPost("ELECTRODRIVER")).getBody(), Marca.class);

        serviceCategoria.postCategoria(new CategoriaDtoPost("ELECTRICOS")).getBody();

        Producto producto2 = new Producto();
        producto2.setDescripcion("Destornillador Eléctrico para tareas de bricolaje");
        producto2.setPrecio_compra(new BigDecimal(1200));
        producto2.setImageURL("https://example.com/destornillador.jpg");
        producto2.setDimensiones("15x5x3 cm");
        producto2.setPeso("0.8 kg");
        producto2.setMaterial("Plástico y Metal");
        producto2.setColor("Amarillo");
        producto2.setPaisOrigen("China");
        producto2.setCodigo_marca("M-ELE-001");
        producto2.setCodigo_categorias(List.of("C-HER-001", "C-ELE-001"));
        producto2.setActivo(true);

        productoDTO2 = serviceProducto.updateProductoInactivo(producto2, p2.getBody().getCodigo()).getBody();

        // PRODUCTO 3
        ProductoPreCarga productoPreCarga3 = new ProductoPreCarga();
        productoPreCarga3.setNombre("Llave Inglesa Ajustable");
        productoPreCarga3.setPrecio_compra(new BigDecimal(1500));
        ResponseEntity<ProductoDtoCompras> p3 = serviceProducto.createProducto(productoPreCarga3);

        modelMapper.map(serviceMarca.crearMarca(new MarcaDtoPost("ADJUSTTOOL")).getBody(), Marca.class);

        serviceCategoria.postCategoria(new CategoriaDtoPost("AJUSTABLES")).getBody();

        Producto producto3 = new Producto();
        producto3.setDescripcion("Llave Inglesa Ajustable para diversas tareas mecánicas");
        producto3.setPrecio_compra(new BigDecimal(1500));
        producto3.setImageURL("https://example.com/llave-inglesa.jpg");
        producto3.setDimensiones("25x8x2 cm");
        producto3.setPeso("0.7 kg");
        producto3.setMaterial("Acero Cromado");
        producto3.setColor("Plateado");
        producto3.setPaisOrigen("Alemania");
        producto3.setCodigo_marca("M-ADJ-001");
        producto3.setCodigo_categorias(List.of("C-HER-001", "C-AJU-001"));
        producto3.setActivo(true);

        productoDTO3 = serviceProducto.updateProductoInactivo(producto3, p3.getBody().getCodigo()).getBody();

        // PRODUCTO 4
        ProductoPreCarga productoPreCarga4 = new ProductoPreCarga();
        productoPreCarga4.setNombre("Cinta Métrica");
        productoPreCarga4.setPrecio_compra(new BigDecimal(500));
        ResponseEntity<ProductoDtoCompras> p4 = serviceProducto.createProducto(productoPreCarga4);

        modelMapper.map(serviceMarca.crearMarca(new MarcaDtoPost("METRICATRACK")).getBody(), Marca.class);

        serviceCategoria.postCategoria(new CategoriaDtoPost("MEDICION")).getBody();

        Producto producto4 = new Producto();
        producto4.setDescripcion("Cinta Métrica de 5 metros para mediciones precisas");
        producto4.setPrecio_compra(new BigDecimal(500));
        producto4.setImageURL("https://example.com/cinta-metrica.jpg");
        producto4.setDimensiones("5x5x1 cm");
        producto4.setPeso("0.2 kg");
        producto4.setMaterial("Plástico y Metal");
        producto4.setColor("Rojo");
        producto4.setPaisOrigen("Italia");
        producto4.setCodigo_marca("M-MET-001");
        producto4.setCodigo_categorias(List.of("C-HER-001", "C-MED-001"));
        producto4.setActivo(true);

        productoDTO4 = serviceProducto.updateProductoInactivo(producto4, p4.getBody().getCodigo()).getBody();
    }
    @Transactional
    @Test
    public void testGetAllDescuentos() {
        DiscountDTOPost discountDTOPost = new DiscountDTOPost();
        discountDTOPost.setNombre("Descuento 1");
        discountDTOPost.setDescripcion("Descripción de Descuento 1");
        discountDTOPost.setActivo(true);
        discountDTOPost.setCodigo_producto("P-MAR-001");
        serviceDiscount.postDescuento(discountDTOPost);

        DiscountDTOPost discountDTOPost2 = new DiscountDTOPost();
        discountDTOPost2.setNombre("Descuento 2");
        discountDTOPost2.setDescripcion("Descripción de Descuento 2");
        discountDTOPost2.setActivo(true);
        discountDTOPost2.setCodigo_producto("P-DES-001");
        serviceDiscount.postDescuento(discountDTOPost2);

        ResponseEntity<List<DiscountDTOProducto>> response = serviceDiscount.getAllDescuentos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
    @Transactional
    @Test
    public void testGetDescuentoByCodigo(){
        DiscountDTOPost discountDTOPost = new DiscountDTOPost();
        discountDTOPost.setNombre("Descuento 1");
        discountDTOPost.setDescripcion("Descripción de Descuento 1");
        discountDTOPost.setActivo(true);
        discountDTOPost.setCodigo_producto("P-MAR-001");
        ResponseEntity<DiscountDTO> responsePost = serviceDiscount.postDescuento(discountDTOPost);

        ResponseEntity<DiscountDTOProducto> response = serviceDiscount.getDescuentoByCodigo(responsePost.getBody().getCodigo());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Descuento 1", response.getBody().getNombre());
    }
    @Transactional
    @Test
    public void testGetDescuentoByNombre(){
        DiscountDTOPost discountDTOPost = new DiscountDTOPost();
        discountDTOPost.setNombre("Descuento 1");
        discountDTOPost.setDescripcion("Descripción de Descuento 1");
        discountDTOPost.setActivo(true);
        discountDTOPost.setCodigo_producto("P-MAR-001");
        serviceDiscount.postDescuento(discountDTOPost);

        ResponseEntity<DiscountDTOProducto> response = serviceDiscount.getDescuentoByNombre("Descuento 1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("D-DES-001", response.getBody().getCodigo());
    }
    @Transactional
    @Test
    public void testGetDescuentosByProductCode(){
        DiscountDTOPost discountDTOPost = new DiscountDTOPost();
        discountDTOPost.setNombre("Descuento 1");
        discountDTOPost.setDescripcion("Descripción de Descuento 1");
        discountDTOPost.setActivo(true);
        discountDTOPost.setCodigo_producto("P-MAR-001");
        serviceDiscount.postDescuento(discountDTOPost);

        DiscountDTOPost discountDTOPost2 = new DiscountDTOPost();
        discountDTOPost2.setNombre("Descuento 2");
        discountDTOPost2.setDescripcion("Descripción de Descuento 2");
        discountDTOPost2.setActivo(true);
        discountDTOPost2.setCodigo_producto("P-MAR-001");
        serviceDiscount.postDescuento(discountDTOPost2);

        ResponseEntity<List<DiscountDTO>> response = serviceDiscount.getAllDescuentosByProductCode("P-MAR-001");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
    @Transactional
    @Test
    public void testPostDescuento(){
        DiscountDTOPost discountDTOPost = new DiscountDTOPost();
        discountDTOPost.setNombre("Descuento 1");
        discountDTOPost.setDescripcion("Descripción de Descuento 1");
        discountDTOPost.setActivo(true);
        discountDTOPost.setCodigo_producto("P-MAR-001");
        ResponseEntity<DiscountDTO> response = serviceDiscount.postDescuento(discountDTOPost);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Descuento 1", response.getBody().getNombre());
    }
    @Transactional
    @Test
    public void testDeleteDescuento(){
        DiscountDTOPost discountDTOPost = new DiscountDTOPost();
        discountDTOPost.setNombre("Descuento 1");
        discountDTOPost.setDescripcion("Descripción de Descuento 1");
        discountDTOPost.setActivo(true);
        discountDTOPost.setCodigo_producto("P-MAR-001");
        ResponseEntity<DiscountDTO> response = serviceDiscount.postDescuento(discountDTOPost);

        ResponseEntity<DiscountDTODelete> response2 = serviceDiscount.deleteDescuento(response.getBody().getCodigo());

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals("Se ha inhabilitado el descuento con codigo: "+response.getBody().getCodigo(), response2.getBody().getMessage());
    }
    @Transactional
    @Test
    public void testPutDescuento(){
        DiscountDTOPost discountDTOPost = new DiscountDTOPost();
        discountDTOPost.setNombre("Descuento 1");
        discountDTOPost.setDescripcion("Descripción de Descuento 1");
        discountDTOPost.setActivo(true);
        discountDTOPost.setCodigo_producto("P-MAR-001");
        ResponseEntity<DiscountDTO> response = serviceDiscount.postDescuento(discountDTOPost);

        DiscountDTOPut discountDTOPut = new DiscountDTOPut();
        discountDTOPut.setNombre("Descuento 2");
        discountDTOPut.setDescripcion("Descripción de Descuento 2");
        discountDTOPut.setActivo(true);
        discountDTOPut.setCodigo_producto("P-MAR-001");
        ResponseEntity<DiscountDTOPut> response2 = serviceDiscount.putDescuento(discountDTOPut, response.getBody().getCodigo());

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals("Descuento 2", response2.getBody().getNombre());
    }
}
