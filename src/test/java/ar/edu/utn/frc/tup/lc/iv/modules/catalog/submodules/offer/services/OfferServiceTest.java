package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.models.Marca;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.services.ServiceMarca;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.services.ServiceCategoria;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos.*;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.dtos.*;
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

@SpringBootTest
public class OfferServiceTest {

    @Autowired
    private ServiceOferta serviceOferta;

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
    public void testGetAllOfertas() {
        OfferDTOPost offerDTOPost = new OfferDTOPost();
        offerDTOPost.setNombre("Oferta 1");
        offerDTOPost.setDescripcion("Descripción de Oferta 1");
        offerDTOPost.setActivo(true);
        offerDTOPost.setCodigo_producto("P-MAR-001");
        offerDTOPost.setPrecio_oferta(BigDecimal.valueOf(500));
        serviceOferta.postOferta(offerDTOPost);

        OfferDTOPost offerDTOPost2 = new OfferDTOPost();
        offerDTOPost2.setNombre("Oferta 2");
        offerDTOPost2.setDescripcion("Oferta de Oferta 2");
        offerDTOPost2.setActivo(true);
        offerDTOPost2.setCodigo_producto("P-DES-001");
        offerDTOPost2.setPrecio_oferta(BigDecimal.valueOf(500));
        serviceOferta.postOferta(offerDTOPost2);

        ResponseEntity<List<OfferDTOProducto>> response = serviceOferta.getAllOfertas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
    @Transactional
    @Test
    public void testGetOfertaByCodigo(){
        OfferDTOPost offerDTOPost = new OfferDTOPost();
        offerDTOPost.setNombre("Oferta 1");
        offerDTOPost.setDescripcion("Descripción de Oferta 1");
        offerDTOPost.setActivo(true);
        offerDTOPost.setCodigo_producto("P-MAR-001");
        offerDTOPost.setPrecio_oferta(BigDecimal.valueOf(500));
        ResponseEntity<OfferDTO> responsePost =serviceOferta.postOferta(offerDTOPost);

        ResponseEntity<OfferDTOProducto> response = serviceOferta.getOfertaByCodigo(responsePost.getBody().getCodigo());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Oferta 1", response.getBody().getNombre());
    }
    @Transactional
    @Test
    public void testGetDescuentoByNombre(){
        OfferDTOPost offerDTOPost = new OfferDTOPost();
        offerDTOPost.setNombre("Oferta 1");
        offerDTOPost.setDescripcion("Descripción de Oferta 1");
        offerDTOPost.setActivo(true);
        offerDTOPost.setCodigo_producto("P-MAR-001");
        offerDTOPost.setPrecio_oferta(BigDecimal.valueOf(500));
        ResponseEntity<OfferDTO> responsePost =serviceOferta.postOferta(offerDTOPost);

        ResponseEntity<OfferDTOProducto> response = serviceOferta.getOfertaByNombre(responsePost.getBody().getNombre());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("O-OFE-001", response.getBody().getCodigo());
    }
    @Transactional
    @Test
    public void testGetAllOfertasByProductCode(){
        OfferDTOPost offerDTOPost = new OfferDTOPost();
        offerDTOPost.setNombre("Descuento 1");
        offerDTOPost.setDescripcion("Descripción de Descuento 1");
        offerDTOPost.setActivo(true);
        offerDTOPost.setCodigo_producto("P-MAR-001");
        offerDTOPost.setPrecio_oferta(BigDecimal.valueOf(500));
        serviceOferta.postOferta(offerDTOPost);

        OfferDTOPost offerDTOPost2 = new OfferDTOPost();
        offerDTOPost2.setNombre("Descuento 2");
        offerDTOPost2.setDescripcion("Descripción de Descuento 2");
        offerDTOPost2.setActivo(true);
        offerDTOPost2.setCodigo_producto("P-MAR-001");
        offerDTOPost2.setPrecio_oferta(BigDecimal.valueOf(650));
        serviceOferta.postOferta(offerDTOPost2);

        ResponseEntity<List<OfferDTO>> response = serviceOferta.getAllOfertasByProductCode("P-MAR-001");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }
    @Transactional
    @Test
    public void testPostOferta(){
        OfferDTOPost offerDTOPost = new OfferDTOPost();
        offerDTOPost.setNombre("Oferta 1");
        offerDTOPost.setDescripcion("Descripción de Oferta 1");
        offerDTOPost.setActivo(true);
        offerDTOPost.setCodigo_producto("P-MAR-001");
        offerDTOPost.setPrecio_oferta(BigDecimal.valueOf(500));
        ResponseEntity<OfferDTO> response = serviceOferta.postOferta(offerDTOPost);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Oferta 1", response.getBody().getNombre());
    }
    @Transactional
    @Test
    public void testDeleteOferta(){
        OfferDTOPost offerDTOPost = new OfferDTOPost();
        offerDTOPost.setNombre("Descuento 1");
        offerDTOPost.setDescripcion("Descripción de Descuento 1");
        offerDTOPost.setActivo(true);
        offerDTOPost.setCodigo_producto("P-MAR-001");
        offerDTOPost.setPrecio_oferta(BigDecimal.valueOf(500));
        ResponseEntity<OfferDTO> response = serviceOferta.postOferta(offerDTOPost);

        ResponseEntity<OfferDTODelete> response2 = serviceOferta.deleteOferta(response.getBody().getCodigo());

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals("Se ha inhabilitado la oferta con codigo: "+response.getBody().getCodigo(), response2.getBody().getMessage());
    }
    @Transactional
    @Test
    public void testPutOferta(){
        OfferDTOPost offerDTOPost = new OfferDTOPost();
        offerDTOPost.setNombre("Descuento 1");
        offerDTOPost.setDescripcion("Descripción de Descuento 1");
        offerDTOPost.setActivo(true);
        offerDTOPost.setCodigo_producto("P-MAR-001");
        offerDTOPost.setPrecio_oferta(BigDecimal.valueOf(500));
        ResponseEntity<OfferDTO> response = serviceOferta.postOferta(offerDTOPost);

        OfferDTOPut offerDTOPut = new OfferDTOPut();
        offerDTOPut.setNombre("Descuento 2");
        offerDTOPut.setDescripcion("Descripción de Descuento 2");
        offerDTOPut.setActivo(true);
        offerDTOPut.setCodigo_producto("P-MAR-001");
        offerDTOPut.setPrecio_oferta(BigDecimal.valueOf(650));
        ResponseEntity<OfferDTOPut> response2 = serviceOferta.putOferta(offerDTOPut, response.getBody().getCodigo());

        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals("Descuento 2", response2.getBody().getNombre());
    }
}
