package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.catalog.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.common.CatalogResponseDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.create.CatalogCreateDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.update.UpdateCatalogDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.entities.CatalogoEntity;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.repositories.CatalogJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.services.ServiceCatalog;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.models.Marca;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.services.ServiceMarca;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.services.ServiceCategoria;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDtoCompras;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.Producto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.ProductoPreCarga;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.services.ServiceProducto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CatalogServiceTest {
    @Autowired
    private CatalogJpaRepository catalogJpaRepository;
    @Autowired
    private ServiceCatalog serviceCatalog;
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
    public void getAllCatalogsTest() {
        List<String> codigosProductos = new ArrayList<>();
        codigosProductos.add(productoDTO.getCodigo());
        codigosProductos.add(productoDTO2.getCodigo());
        codigosProductos.add(productoDTO3.getCodigo());
        codigosProductos.add(productoDTO4.getCodigo());

        serviceCatalog.postCatalog(new CatalogCreateDTO("Catalogo 1","Catalogo de productos normales",codigosProductos));

        List<String> codigosProductos2 = new ArrayList<>();
        codigosProductos2.add(productoDTO.getCodigo());
        codigosProductos2.add(productoDTO3.getCodigo());

        serviceCatalog.postCatalog(new CatalogCreateDTO("Catalogo 2","Catalogo de productos de ferreteria",codigosProductos2));

        ResponseEntity<List<CatalogResponseDTO>> catalogsResponseDTO = serviceCatalog.getAllCatalogs();

        assertEquals(2, catalogsResponseDTO.getBody().size());
        assertEquals(HttpStatusCode.valueOf(200), catalogsResponseDTO.getStatusCode());

        assertEquals("CATALOGO 1", catalogsResponseDTO.getBody().get(0).getNombre());
        assertEquals("CA-CAT-001", catalogsResponseDTO.getBody().get(0).getCodigo());
        assertEquals(4, catalogsResponseDTO.getBody().get(0).getProductos_asociados().size());

        assertEquals("CATALOGO 2", catalogsResponseDTO.getBody().get(1).getNombre());
        assertEquals("CA-CAT-002", catalogsResponseDTO.getBody().get(1).getCodigo());
        assertEquals(2, catalogsResponseDTO.getBody().get(1).getProductos_asociados().size());
    }

    @Transactional
    @Test
    public void getCatalogByCodigoTest() {
        List<String> codigosProductos = new ArrayList<>();
        codigosProductos.add(productoDTO.getCodigo());
        codigosProductos.add(productoDTO2.getCodigo());
        codigosProductos.add(productoDTO3.getCodigo());
        codigosProductos.add(productoDTO4.getCodigo());

        ResponseEntity<CatalogResponseDTO> catalog = serviceCatalog.postCatalog(new CatalogCreateDTO("Catalogo 1","Catalogo de productos normales",codigosProductos));

        ResponseEntity<CatalogResponseDTO> catalogsResponseDTO = serviceCatalog.getCatalogByCode(catalog.getBody().getCodigo());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceCatalog.getCatalogByCode("CA-PLO-001");
        });
        CatalogoEntity catalogoEntity = catalogJpaRepository.getByCodigo(catalog.getBody().getCodigo());
        assertEquals(HttpStatusCode.valueOf(200), catalogsResponseDTO.getStatusCode());

        assertEquals("CATALOGO 1", catalogoEntity.getNombre());
        assertEquals("CA-CAT-001", catalogsResponseDTO.getBody().getCodigo());
        assertEquals(4, catalogsResponseDTO.getBody().getProductos_asociados().size());
        assertEquals("El catálogo con codigo CA-PLO-001 no existe.", exception.getMessage());
    }

    @Transactional
    @Test
    public void postCatalogTest() {
        List<String> codigosProductos = new ArrayList<>();
        codigosProductos.add(productoDTO.getCodigo());
        codigosProductos.add(productoDTO2.getCodigo());
        codigosProductos.add(productoDTO3.getCodigo());
        codigosProductos.add(productoDTO4.getCodigo());

        ResponseEntity<CatalogResponseDTO> catalog = serviceCatalog.postCatalog(new CatalogCreateDTO("Catalogo 1","Catalogo de productos normales",codigosProductos));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            serviceCatalog.postCatalog(new CatalogCreateDTO("","Productos varios",codigosProductos));
        });
        RuntimeException exception2 = assertThrows(RuntimeException.class, () -> {
            serviceCatalog.postCatalog(new CatalogCreateDTO("Catalogo 1","Catalogo de productos normales",codigosProductos));
        });
        RuntimeException exception3 = assertThrows(RuntimeException.class, () -> {
            String name = "asdasdasdasdasdasdasdasdsadsadsadasdsadsadasdsadasa";
            serviceCatalog.postCatalog(new CatalogCreateDTO(name,"Productos varios", codigosProductos));
        });
        RuntimeException exception4 = assertThrows(RuntimeException.class, () -> {
            codigosProductos.add("P-AAA-001");
            serviceCatalog.postCatalog(new CatalogCreateDTO("Catalogo 2","Productos varios", codigosProductos));
        });

        assertEquals(HttpStatusCode.valueOf(200), catalog.getStatusCode());
        assertEquals("CATALOGO 1", catalog.getBody().getNombre());
        assertEquals("CA-CAT-001", catalog.getBody().getCodigo());
        assertEquals(4, catalog.getBody().getProductos_asociados().size());
        assertEquals("El nombre del catalogo no puede estar vacío.", exception.getMessage());
        assertEquals("Ya existe un catalogo con ese nombre.", exception2.getMessage());
        assertEquals("El nombre del catalogo no puede tener más de 50 caracteres.", exception3.getMessage());
        assertEquals("El producto con codigo 'P-AAA-001' no existe.",exception4.getMessage());
    }

    @Transactional
    @Test
    public void deleteCatalogTest() {
        List<String> codigosProductos = new ArrayList<>();
        codigosProductos.add(productoDTO.getCodigo());
        codigosProductos.add(productoDTO2.getCodigo());
        codigosProductos.add(productoDTO3.getCodigo());
        codigosProductos.add(productoDTO4.getCodigo());

        ResponseEntity<CatalogResponseDTO> catalog = serviceCatalog.postCatalog(new CatalogCreateDTO("Catalogo 1","Catalogo de productos normales",codigosProductos));


        ResponseEntity<Boolean> response = serviceCatalog.deleteCatalogByCode(catalog.getBody().getCodigo());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceCatalog.deleteCatalogByCode("CA-PLO-001");
        });

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().booleanValue());
        assertEquals("El catálogo con codigo CA-PLO-001 no existe.", exception.getMessage());
    }

    @Transactional
    @Test
    public void putCatalogTest() {
        List<String> codigosProductos = new ArrayList<>();
        codigosProductos.add(productoDTO.getCodigo());
        codigosProductos.add(productoDTO2.getCodigo());
        codigosProductos.add(productoDTO3.getCodigo());

        ResponseEntity<CatalogResponseDTO> catalog = serviceCatalog.postCatalog(new CatalogCreateDTO("Catalogo 1","Catalogo de productos normales",codigosProductos));

        codigosProductos.add(productoDTO4.getCodigo());
        UpdateCatalogDTO update = new UpdateCatalogDTO("Descripcion actualizada","Catalogo actualizado",codigosProductos);
        ResponseEntity<CatalogResponseDTO> response = serviceCatalog.updateCatalogByCode(catalog.getBody().getCodigo(), update);

        RuntimeException exception2 = assertThrows(RuntimeException.class, () -> {
            serviceCatalog.updateCatalogByCode(catalog.getBody().getCodigo(),update);
        });
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            update.setNombre("");
            serviceCatalog.updateCatalogByCode(catalog.getBody().getCodigo(),update);
        });
        RuntimeException exception3 = assertThrows(RuntimeException.class, () -> {
            update.setNombre("asdasdasdasdasdasdasdasdsadsadsadasdsadsadasdsadasa");
            serviceCatalog.updateCatalogByCode(catalog.getBody().getCodigo(),update);
        });
        RuntimeException exception4 = assertThrows(RuntimeException.class, () -> {
            codigosProductos.add("P-AAA-001");
            update.setProductos_asociados(codigosProductos);
            update.setNombre("Catalogo actualizado2");
            serviceCatalog.updateCatalogByCode(catalog.getBody().getCodigo(),update);
        });
        EntityNotFoundException exception5 = assertThrows(EntityNotFoundException.class, () -> {
            serviceCatalog.updateCatalogByCode("CA-PLO-001",update);
        });

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("CA-CAT-001", response.getBody().getCodigo());
        assertEquals("CATALOGO ACTUALIZADO", response.getBody().getNombre());
        assertEquals("DESCRIPCION ACTUALIZADA",response.getBody().getDescripcion());
        assertEquals(4,response.getBody().getProductos_asociados().size());

        assertEquals("El nombre del catalogo no puede estar vacío.", exception.getMessage());
        assertEquals("Ya existe un catalogo con ese nombre.", exception2.getMessage());
        assertEquals("El nombre del catalogo no puede tener más de 50 caracteres.", exception3.getMessage());
        assertEquals("El producto con codigo 'P-AAA-001' no existe.",exception4.getMessage());
        assertEquals("El catálogo con codigo CA-PLO-001 no existe.", exception5.getMessage());
    }
}
