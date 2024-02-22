package ar.edu.utn.frc.tup.lc.iv.common;

import ar.edu.utn.frc.tup.lc.iv.common.services.ServiceCommonImpl;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.repositories.CategoriaJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.ProductoPreCarga;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.repositories.ProductoJpaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ServiceCommonTest {

    @Mock
    private ProductoJpaRepository productoJpaRepository;

    @Mock
    private CategoriaJpaRepository categoriaJpaRepository;

    @Mock
    private ModelMapper modelMapper;

    @Autowired
    private ServiceCommonImpl serviceCommon;

    @Test
    public void createCodeForProductoTest() {
        ProductoPreCarga producto = new ProductoPreCarga();
        producto.setNombre("Tornillo");

        when(productoJpaRepository.existsByCodigo("P-TOR-001")).thenReturn(false);

        String code = serviceCommon.createCode(producto);

        //verify(productoJpaRepository).existsByCodigo("P-MI-001");
        assertEquals("P-TOR-001", code);
    }

    //TODO: CORREGIR
    /*@Test
    public void mapearCategoriasToProductsTest() {

        ProductoEntity productoEntity = new ProductoEntity();
        CategoriaEntity categoria1 = new CategoriaEntity();
        categoria1.setCodigo("CAT-001");
        categoria1.setNombre("Categoría 1");
        CategoriaEntity categoria2 = new CategoriaEntity();
        categoria2.setCodigo("CAT-002");
        categoria2.setNombre("Categoría 2");
        productoEntity.getCategoriaEntities().add(categoria1);
        productoEntity.getCategoriaEntities().add(categoria2);

        ProductoDTO productoDTO = new ProductoDTO();
        List<CategoriaDto> categoriasMapped = new ArrayList<>();

        //when(modelMapper.map(categoria1, CategoriaDto.class)).thenReturn(new CategoriaDto("CAT-001", "Categoría 1"));
        //when(modelMapper.map(categoria2, CategoriaDto.class)).thenReturn(new CategoriaDto("CAT-002", "Categoría 2"));

        serviceCommon.mapearCategoriasToProducts(productoDTO, categoriasMapped, productoEntity);

        assertEquals(2, productoDTO.getCategorias().size());
        assertEquals("CAT-001", productoDTO.getCategorias().get(0).getCodigo());
        assertEquals("Categoría 1", productoDTO.getCategorias().get(0).getNombre());
        assertEquals("CAT-002", productoDTO.getCategorias().get(1).getCodigo());
        assertEquals("Categoría 2", productoDTO.getCategorias().get(1).getNombre());
    }*/

    //TODO: CORREGIR
    /*@Test
    public void getAllDescuentosByProductCodeTest() {
        // Crea un código de producto de prueba
        String productCode = "P-TOR-001";

        // Crea una lista de DescuentoEntity de prueba
        List<DescuentoEntity> descuentos = new ArrayList<>();
        DescuentoEntity descuento1 = new DescuentoEntity();
        descuento1.setId('D-001');
        descuento1.setNombre("Descuento 1");
        DescuentoEntity descuento2 = new DescuentoEntity();
        descuento2.setId('2L');
        descuento2.setNombre("Descuento 2");
        descuentos.add(descuento1);
        descuentos.add(descuento2);

        // Configura el comportamiento de discountJpaRepository para simular la obtención de descuentos
        when(discountJpaRepository.getAllByProducto_Codigo(productCode)).thenReturn(descuentos);

        // Configura el comportamiento de ModelMapper para simular el mapeo de DescuentoEntity a DiscountDTOSimple
        when(modelMapper.map(descuento1, DiscountDTOSimple.class)).thenReturn(new DiscountDTOSimple(1L, "Descuento 1"));
        when(modelMapper.map(descuento2, DiscountDTOSimple.class)).thenReturn(new DiscountDTOSimple(2L, "Descuento 2"));

        // Llama al método que deseas probar
        List<DiscountDTOSimple> descuentosDto = serviceCommon.getAllDescuentosByProductCode(productCode);

        // Verifica que se obtuvieron los descuentos correctamente y se mapearon a DTO
        assertEquals(2, descuentosDto.size());
        assertEquals(1L, descuentosDto.get(0).getId());
        assertEquals("Descuento 1", descuentosDto.get(0).getNombre());
        assertEquals(2L, descuentosDto.get(1).getId());
        assertEquals("Descuento 2", descuentosDto.get(1).getNombre());
    }*/

    //TODO: CORREGIR
    /*@Test
    public void getAllOfertasByProductCodeTest() {
        // Crea un código de producto de prueba
        String productCode = "P-TOR-001";

        // Crea una lista de OfertaEntity de prueba
        List<OfertaEntity> ofertas = new ArrayList<>();
        OfertaEntity oferta1 = new OfertaEntity();
        oferta1.setId(1L);
        oferta1.setNombre("Oferta 1");
        OfertaEntity oferta2 = new OfertaEntity();
        oferta2.setId(2L);
        oferta2.setNombre("Oferta 2");
        ofertas.add(oferta1);
        ofertas.add(oferta2);

        // Configura el comportamiento de offerJpaRepository para simular la obtención de ofertas
        when(offerJpaRepository.getAllByProducto_Codigo(productCode)).thenReturn(ofertas);

        // Configura el comportamiento de ModelMapper para simular el mapeo de OfertaEntity a OfferDTOSimple
        when(modelMapper.map(oferta1, OfferDTOSimple.class)).thenReturn(new OfferDTOSimple(1L, "Oferta 1"));
        when(modelMapper.map(oferta2, OfferDTOSimple.class)).thenReturn(new OfferDTOSimple(2L, "Oferta 2"));

        // Llama al método que deseas probar
        List<OfferDTOSimple> ofertasDto = serviceCommon.getAllOfertasByProductCode(productCode);

        // Verifica que se obtuvieron las ofertas correctamente y se mapearon a DTO
        assertEquals(2, ofertasDto.size());
        assertEquals(1L, ofertasDto.get(0).getId());
        assertEquals("Oferta 1", ofertasDto.get(0).getNombre());
        assertEquals(2L, ofertasDto.get(1).getId());
        assertEquals("Oferta 2", ofertasDto.get(1).getNombre());
    }*/
}
