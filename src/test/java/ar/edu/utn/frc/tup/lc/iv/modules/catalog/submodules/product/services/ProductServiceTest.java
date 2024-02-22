package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.models.Marca;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.services.ServiceMarca;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.services.ServiceCategoria;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTODelete;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDtoCompras;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.Producto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.ProductoPreCarga;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.repositories.ProductoJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {
    @Mock
    private ProductoJpaRepository productoJpaRepository;
    @Autowired
    private ServiceProducto serviceProducto;
    @Autowired
    private ServiceMarca serviceMarca;
    @Autowired
    private ServiceCategoria serviceCategoria;
    @Autowired
    ModelMapper modelMapper;

    @Transactional
    @Test
    public void getAllProductsTest() {
        ProductoPreCarga productoPreCarga = new ProductoPreCarga();
        productoPreCarga.setNombre("Producto 1");
        productoPreCarga.setPrecio_compra(new BigDecimal(1000));
        serviceProducto.createProducto(productoPreCarga);

        ProductoPreCarga productoPreCarga2 = new ProductoPreCarga();
        productoPreCarga2.setNombre("Producto 2");
        productoPreCarga2.setPrecio_compra(new BigDecimal(2000));
        serviceProducto.createProducto(productoPreCarga2);

        ResponseEntity<List<ProductoDTO>> listaDto = serviceProducto.getAllProductos();

        assertEquals(2, listaDto.getBody().size());
        assertEquals(HttpStatusCode.valueOf(200), listaDto.getStatusCode());

        assertEquals("PRODUCTO 1", listaDto.getBody().get(0).getNombre());
        assertEquals("P-PRO-001", listaDto.getBody().get(0).getCodigo());
        assertEquals("1000", listaDto.getBody().get(0).getPrecio_compra());

        assertEquals("PRODUCTO 2", listaDto.getBody().get(1).getNombre());
        assertEquals("P-PRO-002", listaDto.getBody().get(1).getCodigo());
        assertEquals("2000", listaDto.getBody().get(1).getPrecio_compra());
    }
    @Transactional
    @Test
    public void getProductByCodigoTest() {
        ProductoPreCarga productoPreCarga = new ProductoPreCarga();
        productoPreCarga.setNombre("Producto 1");
        productoPreCarga.setPrecio_compra(new BigDecimal(1000));
        ResponseEntity<ProductoDtoCompras> p =  serviceProducto.createProducto(productoPreCarga);

        ResponseEntity<ProductoDTO> response = serviceProducto.getProductoByCode(p.getBody().getCodigo());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceProducto.getProductoByCode("P-PAP-001");
        });

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("P-PRO-001", response.getBody().getCodigo());
        assertEquals("1000", response.getBody().getPrecio_compra());
        assertEquals("El producto con codigo P-PAP-001 no existe.", exception.getMessage());
    }


    @Transactional
    @Test
    public void postProductTest() {
        ProductoPreCarga productoPreCarga = new ProductoPreCarga();
        productoPreCarga.setNombre("Clavos de Metal");
        productoPreCarga.setPrecio_compra(new BigDecimal(1000));
        ResponseEntity<ProductoDtoCompras> p =  serviceProducto.createProducto(productoPreCarga);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            serviceProducto.createProducto(new ProductoPreCarga("",new BigDecimal(100)));
        });
        RuntimeException exception2 = assertThrows(RuntimeException.class, () -> {
            serviceProducto.createProducto(productoPreCarga);
        });
        RuntimeException exception3 = assertThrows(RuntimeException.class, () -> {
            String name = "asdasdasdasdasdasdasdasdsadsadsadasdsadsadasdsadasa";
            serviceProducto.createProducto(new ProductoPreCarga(name,new BigDecimal(100)));
        });

        assertEquals(HttpStatusCode.valueOf(200), p.getStatusCode());
        assertEquals("CLAVOS DE METAL", p.getBody().getNombre());
        assertEquals("P-CLA-001", p.getBody().getCodigo());
        assertEquals(new BigDecimal(1000), p.getBody().getPrecio_compra());
        assertEquals("El nombre del producto no puede estar vacío.", exception.getMessage());
        assertEquals("Ya existe un producto con ese nombre.", exception2.getMessage());
        assertEquals("El nombre del producto no puede tener más de 50 caracteres.", exception3.getMessage());
    }

    @Transactional
    @Test
    public void deleteProductTest() {
        ProductoPreCarga productoPreCarga = new ProductoPreCarga();
        productoPreCarga.setNombre("Clavos de Metal");
        productoPreCarga.setPrecio_compra(new BigDecimal(1000));
        ResponseEntity<ProductoDtoCompras> p =  serviceProducto.createProducto(productoPreCarga);
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceProducto.deleteProducto("P-PAP-001");
        });

        ResponseEntity<ProductoDTODelete> response = serviceProducto.deleteProducto(p.getBody().getCodigo());

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().isStatus());
        assertEquals("Se ha inhabilitado el producto con codigo: " +p.getBody().getCodigo(),response.getBody().getMessage());
        assertEquals("El producto con codigo 'P-PAP-001' no existe.", exception.getMessage());
    }
    @Transactional
    @Test
    public void putProductTest() {
        ProductoPreCarga productoPreCarga = new ProductoPreCarga();
        productoPreCarga.setNombre("Clavos de Acero");
        productoPreCarga.setPrecio_compra(new BigDecimal(1000));
        ResponseEntity<ProductoDtoCompras> p =  serviceProducto.createProducto(productoPreCarga);

        Marca marca = modelMapper.map(serviceMarca.crearMarca(new MarcaDtoPost("STEEL")).getBody(),Marca.class);

        CategoriaDto categoria1 = serviceCategoria.postCategoria(new CategoriaDtoPost("CLAVOS")).getBody();
        CategoriaDto categoria2 = serviceCategoria.postCategoria(new CategoriaDtoPost("FERRETERIA")).getBody();

        Producto producto = new Producto();
        producto.setDescripcion("Clavos de Acero de dimensiones 10x5x3 y peso de 0.5kg");
        producto.setPrecio_compra(new BigDecimal(100));
        producto.setImageURL("https://centrosider.com.ar/wp-content/uploads/2020/08/clavos-punta-paris-acindar-1.jpg");
        producto.setDimensiones("10x5x3 cm");
        producto.setPeso("0.5 kg");
        producto.setMaterial("Acero");
        producto.setColor("Plateado");
        producto.setPaisOrigen("Argentina");
        producto.setCodigo_marca("M-STE-001");
        producto.setCodigo_categorias(List.of("C-CLA-001", "C-FER-001"));
        producto.setActivo(true);

        ResponseEntity<ProductoDTO> response = serviceProducto.updateProductoInactivo(producto, p.getBody().getCodigo());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceProducto.updateProductoInactivo(producto,"P-PAP-001");
        });
        RuntimeException exception2 = assertThrows(RuntimeException.class, () -> {
            producto.setCodigo_marca("M-LPL-001");
            serviceProducto.updateProductoInactivo(producto, p.getBody().getCodigo());
        });
        RuntimeException exception3 = assertThrows(RuntimeException.class, () -> {
            producto.setCodigo_marca("M-STE-001");
            producto.setCodigo_categorias(List.of("C-CLA-001", "C-FER-001","C-FAF-001"));
            serviceProducto.updateProductoInactivo(producto, p.getBody().getCodigo());
        });
        RuntimeException exception4 = assertThrows(RuntimeException.class, () -> {
            producto.setCodigo_categorias(List.of("C-CLA-001", "C-FER-001",""));
            serviceProducto.updateProductoInactivo(producto, p.getBody().getCodigo());
        });

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("P-CLA-001", response.getBody().getCodigo());
        assertEquals("CLAVOS DE ACERO", response.getBody().getNombre());
        assertEquals("CLAVOS DE ACERO DE DIMENSIONES 10X5X3 Y PESO DE 0.5KG",response.getBody().getDescripcion());
        assertEquals("100",response.getBody().getPrecio_compra());
        assertEquals("https://centrosider.com.ar/wp-content/uploads/2020/08/clavos-punta-paris-acindar-1.jpg",response.getBody().getImageURL());
        assertEquals("10X5X3 CM",response.getBody().getDimensiones());
        assertEquals("0.5 KG",response.getBody().getPeso());
        assertEquals("ACERO",response.getBody().getMaterial());
        assertEquals("PLATEADO",response.getBody().getColor());
        assertEquals("ARGENTINA",response.getBody().getPaisOrigen());
        assertEquals(marca,response.getBody().getMarca());
        assertEquals(List.of(categoria1,categoria2),response.getBody().getCategorias());
        assertTrue(response.getBody().getActivo());

        assertEquals("El producto con codigo 'P-PAP-001' no existe.", exception.getMessage());
        assertEquals("El codigo de la marca no existe.", exception2.getMessage());
        assertEquals("El codigo de la categoria no existe.", exception3.getMessage());
        assertEquals("No puedes poner una categoria vacia.", exception4.getMessage());
    }

    @Transactional
    @Test
    public void productosActivosParametersTest() {
        ProductoPreCarga productoPreCarga = new ProductoPreCarga();
        productoPreCarga.setNombre("Clavos de Acero");
        productoPreCarga.setPrecio_compra(new BigDecimal(1000));
        ResponseEntity<ProductoDtoCompras> p =  serviceProducto.createProducto(productoPreCarga);

        ProductoPreCarga productoPreCarga2 = new ProductoPreCarga();
        productoPreCarga2.setNombre("Producto 2");
        productoPreCarga2.setPrecio_compra(new BigDecimal(2000));
        ResponseEntity<ProductoDtoCompras> p2 =  serviceProducto.createProducto(productoPreCarga2);

        ProductoPreCarga productoPreCarga3 = new ProductoPreCarga();
        productoPreCarga3.setNombre("Producto 3");
        productoPreCarga3.setPrecio_compra(new BigDecimal(3000));
        ProductoDtoCompras produc3 = serviceProducto.createProducto(productoPreCarga3).getBody();
        ProductoDTO p3 = serviceProducto.getProductoByCode(produc3.getCodigo()).getBody();

        serviceMarca.crearMarca(new MarcaDtoPost("STEEL"));
        serviceCategoria.postCategoria(new CategoriaDtoPost("CLAVOS"));
        serviceCategoria.postCategoria(new CategoriaDtoPost("FERRETERIA"));

        Producto producto = new Producto();
        producto.setDescripcion("Clavos de Acero de dimensiones 10x5x3 y peso de 0.5kg");
        producto.setPrecio_compra(new BigDecimal(100));
        producto.setImageURL("https://centrosider.com.ar/wp-content/uploads/2020/08/clavos-punta-paris-acindar-1.jpg");
        producto.setDimensiones("10x5x3 cm");
        producto.setPeso("0.5 kg");
        producto.setMaterial("Acero");
        producto.setColor("Plateado");
        producto.setPaisOrigen("Argentina");
        producto.setCodigo_marca("M-STE-001");
        producto.setCodigo_categorias(List.of("C-CLA-001", "C-FER-001"));
        producto.setActivo(true);

        ProductoDTO update1 = serviceProducto.updateProductoInactivo(producto, p.getBody().getCodigo()).getBody();

        Producto producto2 = new Producto();
        producto2.setDescripcion("Ejemplo");
        producto2.setImageURL("Ejemplo");
        producto2.setPrecio_compra(new BigDecimal(2000));
        producto2.setDimensiones("Ejemplo");
        producto2.setPeso("Ejemplo");
        producto2.setMaterial("Ejemplo");
        producto2.setColor("Ejemplo");
        producto2.setPaisOrigen("Ejemplo");
        producto2.setCodigo_marca("");
        producto2.setCodigo_categorias(List.of());
        producto2.setActivo(true);

        ProductoDTO update2 = serviceProducto.updateProductoInactivo(producto2, p2.getBody().getCodigo()).getBody();

        ResponseEntity<List<ProductoDTO>> response = serviceProducto.productosActivosParameters(true);
        ResponseEntity<List<ProductoDTO>> response2 = serviceProducto.productosActivosParameters(false);


        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(List.of(update1,update2),response.getBody());

        assertEquals(HttpStatusCode.valueOf(200), response2.getStatusCode());
        assertEquals(List.of(p3),response2.getBody());
    }

    @Transactional
    @Test
    public void getProdParamsTest() {
        ProductoPreCarga productoPreCarga = new ProductoPreCarga();
        productoPreCarga.setNombre("Clavos de Acero");
        productoPreCarga.setPrecio_compra(new BigDecimal(1000));
        ResponseEntity<ProductoDtoCompras> p =  serviceProducto.createProducto(productoPreCarga);

        ProductoPreCarga productoPreCarga2 = new ProductoPreCarga();
        productoPreCarga2.setNombre("Producto 2");
        productoPreCarga2.setPrecio_compra(new BigDecimal(2000));
        ProductoDtoCompras produc2 = serviceProducto.createProducto(productoPreCarga2).getBody();
        ProductoDTO p2 = serviceProducto.getProductoByCode(produc2.getCodigo()).getBody();

        ProductoPreCarga productoPreCarga3 = new ProductoPreCarga();
        productoPreCarga3.setNombre("Producto 3");
        productoPreCarga3.setPrecio_compra(new BigDecimal(3000));
        ProductoDtoCompras produc3 = serviceProducto.createProducto(productoPreCarga3).getBody();
        ProductoDTO p3 = serviceProducto.getProductoByCode(produc3.getCodigo()).getBody();

        serviceMarca.crearMarca(new MarcaDtoPost("STEEL"));
        serviceCategoria.postCategoria(new CategoriaDtoPost("CLAVOS"));
        serviceCategoria.postCategoria(new CategoriaDtoPost("FERRETERIA"));

        Producto producto = new Producto();
        producto.setDescripcion("Clavos de Acero de dimensiones 10x5x3 y peso de 0.5kg");
        producto.setPrecio_compra(new BigDecimal(100));
        producto.setImageURL("https://centrosider.com.ar/wp-content/uploads/2020/08/clavos-punta-paris-acindar-1.jpg");
        producto.setDimensiones("10x5x3 cm");
        producto.setPeso("0.5 kg");
        producto.setMaterial("Acero");
        producto.setColor("Plateado");
        producto.setPaisOrigen("Argentina");
        producto.setCodigo_marca("M-STE-001");
        producto.setCodigo_categorias(List.of("C-CLA-001", "C-FER-001"));
        producto.setActivo(true);

        ProductoDTO update1 = serviceProducto.updateProductoInactivo(producto, p.getBody().getCodigo()).getBody();

        Producto producto2 = new Producto();
        producto2.setDescripcion("Ejemplo");
        producto2.setImageURL("Ejemplo");
        producto2.setPrecio_compra(new BigDecimal(2000));
        producto2.setDimensiones("Ejemplo");
        producto2.setPeso("Ejemplo");
        producto2.setMaterial("Ejemplo");
        producto2.setColor("Ejemplo");
        producto2.setPaisOrigen("Ejemplo");
        producto2.setCodigo_marca("");
        producto2.setCodigo_categorias(List.of());
        producto2.setActivo(true);

        ProductoDTO update2 = serviceProducto.updateProductoInactivo(producto2, p2.getCodigo()).getBody();

        ResponseEntity<List<ProductoDTO>> response = serviceProducto.getProdParams(null,"PRO",null,null,null,null,null,null);


        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(List.of(update2,p3),response.getBody());
    }
}
