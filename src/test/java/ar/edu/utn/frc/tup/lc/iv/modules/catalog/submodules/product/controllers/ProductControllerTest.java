package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.controllers;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.models.Marca;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTODelete;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDtoCompras;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.Producto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.ProductoPreCarga;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.services.ServiceProducto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductoController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceProducto serviceProducto;

    private final String API_PATH = "/api/products";

    List<ProductoDTO> listProductoDTO;
    ProductoDTO productoDTO,productoDTO2,productoDTO3,productoDTO4;


    @BeforeEach
    public void setup() {
        listProductoDTO = new ArrayList<>();
        productoDTO = new ProductoDTO("P-CLA-001","Clavos de Acero","Clavos de Acero de dimensiones 10x5x3 y peso de 0.5kg","100",
                "110","120","https://centrosider.com.ar/wp-content/uploads/2020/08/clavos-punta-paris-acindar-1.jpg","10x5x3 cm",
                "0.5 kg","Acero","Plateado","Argentina",new Marca("M-STE-001","STEEL"),
                List.of(new CategoriaDto("C-CLA-001","CLAVOS"), new CategoriaDto("C-FER-001","FERRETERIA")),null,null,true);

        productoDTO2 = new ProductoDTO("P-LAM-001", "Lámpara LED", "Lámpara LED de 15W con luz blanca",
                "200", "220", "250",
                "https://example.com/lampara-led.jpg", "10x10x15 cm",
                "0.5 kg", "Plástico", "Blanco", "China", new Marca("M-LED-001", "LED-MARCA"),
                List.of(new CategoriaDto("C-LAM-001", "ILUMINACION"),new CategoriaDto("C-TUB-001", "TUBERIAS")), null, null, true);

        productoDTO3 = new ProductoDTO("P-MAR-001", "Martillo de Hierro", "Martillo de Hierro de dimensiones 15x7x5 y peso de 1kg",
                "150", "160", "180",
                "https://example.com/martillo.jpg", "15x7x5 cm",
                "1 kg", "Hierro", "Negro", "Argentina", new Marca("M-HIE-001", "HIERRO-MARCA"),
                List.of(new CategoriaDto("C-HER-001", "HERRAMIENTAS")), null, null, true);

        productoDTO4 = new ProductoDTO("P-TUB-001", "Tubo de PVC", "Tubo de PVC de 2 metros de longitud y diámetro de 10cm",
                "50", "60", "70",
                "https://example.com/tubo-pvc.jpg", "200x10 cm",
                "1 kg", "PVC", "Blanco", "Argentina", new Marca("M-PVC-001", "PVC-MARCA"),
                List.of(new CategoriaDto("C-TUB-001", "TUBERIAS"),new CategoriaDto("C-LAM-001", "ILUMINACION")), null, null, false);


        listProductoDTO.add(productoDTO);
        listProductoDTO.add(productoDTO2);
        listProductoDTO.add(productoDTO3);
        listProductoDTO.add(productoDTO4);

    }

    @Test
    void getAllProductsEndpointTest() throws Exception {
        when(serviceProducto.getAllProductos()).thenReturn(ResponseEntity.ok(listProductoDTO));

        mockMvc.perform(get(API_PATH).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(listProductoDTO.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty());
    }
    @Test
    void getProductoByCodeEndpointTest() throws Exception {
        String codigoExistente = "P-CLA-001";

        when(serviceProducto.getProductoByCode(codigoExistente)).thenReturn(ResponseEntity.ok(productoDTO));

        mockMvc.perform(get(API_PATH+"/{code}", codigoExistente)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(productoDTO.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(productoDTO.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(productoDTO.getDescripcion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.precio_compra").value(productoDTO.getPrecio_compra()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.precio_minorista").value(productoDTO.getPrecio_minorista()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.precio_mayorista").value(productoDTO.getPrecio_mayorista()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.imageURL").value(productoDTO.getImageURL()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dimensiones").value(productoDTO.getDimensiones()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.peso").value(productoDTO.getPeso()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.material").value(productoDTO.getMaterial()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(productoDTO.getColor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.paisOrigen").value(productoDTO.getPaisOrigen()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.marca.codigo").value(productoDTO.getMarca().getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.marca.nombre").value(productoDTO.getMarca().getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categorias[0].codigo").value(productoDTO.getCategorias().get(0).getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categorias[0].nombre").value(productoDTO.getCategorias().get(0).getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categorias[1].codigo").value(productoDTO.getCategorias().get(1).getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categorias[1].nombre").value(productoDTO.getCategorias().get(1).getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.activo").value(productoDTO.getActivo()));
    }


    @Test
    void createProductEndpointTest() throws Exception {
        ProductoPreCarga productoPreCarga = new ProductoPreCarga("Martillo de Hierro", new BigDecimal(150));
        ProductoDtoCompras productoDtoCompras = new ProductoDtoCompras(productoDTO3.getCodigo(), true, "Operación exitosa, el producto se ha creado correctamente.", productoDTO3.getNombre(), new BigDecimal(productoDTO3.getPrecio_compra()));

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyJson = objectMapper.writeValueAsString(productoPreCarga);

        when(serviceProducto.createProducto(productoPreCarga)).thenReturn(ResponseEntity.ok(productoDtoCompras));

        mockMvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bodyJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(productoDtoCompras.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mensaje").value(productoDtoCompras.getMensaje()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(productoDtoCompras.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.precio_compra").value(productoDtoCompras.getPrecio_compra()));
    }

    @Test
    void bajaDeProductoEndpointTest() throws Exception {
        String codigoProducto = "P-CLA-001";

        ProductoDTODelete productoDTODelete = new ProductoDTODelete(true,"Se ha inhabilitado el producto con codigo: " +codigoProducto);
        when(serviceProducto.deleteProducto(codigoProducto)).thenReturn(ResponseEntity.ok(productoDTODelete));

        mockMvc.perform(delete(API_PATH+"/{code}", codigoProducto)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Se ha inhabilitado el producto con codigo: " +codigoProducto));

    }
    @Test
    void updateProductEndpointTest() throws Exception {
        ProductoDTO prodDTO = listProductoDTO.get(0);
        
        Producto prod = getProducto(prodDTO);

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyJson = objectMapper.writeValueAsString(prod);
        String colorNuevo = "Azul";
        prodDTO.setColor(colorNuevo);

        when(serviceProducto.updateProductoInactivo(prod, prodDTO.getCodigo()))
                .thenReturn(ResponseEntity.ok(prodDTO));

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put(API_PATH + "/{code}",prodDTO.getCodigo())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(bodyJson);

        mockMvc.perform(builder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(prodDTO.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.color").value(colorNuevo));

    }

    @Test
    void getProductoActivosParametersEndpointTest() throws Exception{
        List<ProductoDTO> listaActivos = List.of(productoDTO,productoDTO2,productoDTO3);

        when(serviceProducto.productosActivosParameters(true)).thenReturn(ResponseEntity.ok(listaActivos));

        mockMvc.perform(get(API_PATH+"/actives-inactive")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("activo","true"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(listaActivos.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty());
    }

    @Test
    void getProdParamsEndpointTest() throws Exception{
        List<ProductoDTO> listProd = List.of(productoDTO2,productoDTO4);

        when(serviceProducto.getProdParams(any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(ResponseEntity.ok(listProd));

        mockMvc.perform(get(API_PATH+"/articulos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("codigo","")
                        .param("nombre","")
                        .param("color", "BLAN")
                        .param("material","")
                        .param("precio_desde","")
                        .param("precio_hasta","")
                        .param("codigo_marca","")
                        .param("codigo_categoria", "C-LAM-001", "C-TUB-001"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(listProd.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty());
    }


    // -------------------------------------------------------------
    // ---------------------METODOS PRIVADOS------------------------
    // -------------------------------------------------------------
    private Producto getProducto(ProductoDTO prodDTO){
        List<String> codigoCats = new ArrayList<>();
        for (CategoriaDto c : prodDTO.getCategorias()) {
            codigoCats.add(c.getCodigo());
        }
        return new Producto(prodDTO.getDescripcion() , new BigDecimal(prodDTO.getPrecio_compra()),
                prodDTO.getImageURL() , prodDTO.getDimensiones() , prodDTO.getPeso() ,
                prodDTO.getMaterial() , prodDTO.getColor() , prodDTO.getPaisOrigen() ,
                prodDTO.getMarca().getCodigo() , codigoCats , true);
    }
}
