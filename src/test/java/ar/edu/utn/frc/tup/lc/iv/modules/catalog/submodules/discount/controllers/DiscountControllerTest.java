package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.controllers;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos.*;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.services.ServiceDiscount;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTODescuento;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DiscountController.class)
public class DiscountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServiceDiscount serviceDiscount;
    private final String API_PATH = "/api/discounts";
    List<DiscountDTOProducto> listDiscountsDTOproducto;
    DiscountDTOProducto discountDTOproducto1, discountDTOproducto2, discountDTOproducto3, discountDTOproducto4;
    List<DiscountDTO> listDiscountsDTO;
    DiscountDTO discountDTO1, discountDTO2, discountDTO3, discountDTO4;

    @BeforeEach
    public void setup() {
        listDiscountsDTOproducto = new ArrayList<>();
        listDiscountsDTO = new ArrayList<>();

        ProductoDTODescuento productoDTO1 = new ProductoDTODescuento("P-CLA-001", "Clavos de Acero",
                "Clavos de Acero de dimensiones 10x5x3 y peso de 0.5kg", "100", "110", "120",
                "https://centrosider.com.ar/wp-content/uploads/2020/08/clavos-punta-paris-acindar-1.jpg", "10x5x3 cm",
                "0.5 kg", "Acero", "Plateado", "Argentina", true);

        ProductoDTODescuento productoDTO2 = new ProductoDTODescuento("P-LAM-001", "Lámpara LED",
                "Lámpara LED de 15W con luz blanca", "200", "220", "250",
                "https://example.com/lampara-led.jpg", "10x10x15 cm",
                "0.5 kg", "Plástico", "Blanco", "China", true);

        ProductoDTODescuento productoDTO3 = new ProductoDTODescuento("P-MAR-001", "Martillo de Hierro",
                "Martillo de Hierro de dimensiones 15x7x5 y peso de 1kg", "150", "160", "180",
                "https://example.com/martillo.jpg", "15x7x5 cm",
                "1 kg", "Hierro", "Negro", "Argentina", true);

        ProductoDTODescuento productoDTO4 = new ProductoDTODescuento("P-TUB-001", "Tubo de PVC",
                "Tubo de PVC de 2 metros de longitud y diámetro de 10cm", "50", "60", "70",
                "https://example.com/tubo-pvc.jpg", "200x10 cm",
                "1 kg", "PVC", "Blanco", "Argentina", false);

        discountDTO1 = new DiscountDTO("D-DES-001", "Descuento1", "Descripción Descuento 1", BigDecimal.valueOf(1000), true, "P-CLA-001");
        discountDTO2 = new DiscountDTO("D-DES-002", "Descuento 2", "Descripción Descuento 2", BigDecimal.valueOf(2000), true, "P-LAM-001");
        discountDTOproducto1 = new DiscountDTOProducto("D-DES-001", "Descuento1", "Descripción Descuento 1", BigDecimal.valueOf(1000), true, productoDTO1);
        discountDTOproducto2 = new DiscountDTOProducto("D-DES-002", "Descuento 2", "Descripción Descuento 2", BigDecimal.valueOf(2000), true, productoDTO2);
        discountDTOproducto3 = new DiscountDTOProducto("D-DES-003", "Descuento 3", "Descripción Descuento 3", BigDecimal.valueOf(3000), true, productoDTO3);
        discountDTOproducto4 = new DiscountDTOProducto("D-DES-004", "Descuento 4", "Descripción Descuento 4", BigDecimal.valueOf(4000), true, productoDTO4);

        listDiscountsDTO.add(discountDTO1);
        listDiscountsDTO.add(discountDTO2);
        listDiscountsDTOproducto.add(discountDTOproducto1);
        listDiscountsDTOproducto.add(discountDTOproducto2);
        listDiscountsDTOproducto.add(discountDTOproducto3);
        listDiscountsDTOproducto.add(discountDTOproducto4);
    }


    @Test
    void getAllDescuentosEndpointTest() throws Exception {
        when(serviceDiscount.getAllDescuentos()).thenReturn(ResponseEntity.ok(listDiscountsDTOproducto));

        mockMvc.perform(get(API_PATH).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(listDiscountsDTOproducto.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty());
    }

    @Test
    void getDescuentoByCodigoEndpointTest() throws Exception {
        String codigoExistente = "D-DES-001";

        when(serviceDiscount.getDescuentoByCodigo(codigoExistente)).thenReturn(ResponseEntity.ok(discountDTOproducto1));

        mockMvc.perform(get(API_PATH + "/{codigo}", codigoExistente)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(discountDTOproducto1.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(discountDTOproducto1.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(discountDTOproducto1.getDescripcion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.precio_min").value(discountDTOproducto1.getPrecio_min()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.activo").value(discountDTOproducto1.isActivo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.codigo").value(discountDTOproducto1.getProducto().getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.nombre").value(discountDTOproducto1.getProducto().getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.descripcion").value(discountDTOproducto1.getProducto().getDescripcion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.precio_compra").value(discountDTOproducto1.getProducto().getPrecio_compra()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.precio_mayorista").value(discountDTOproducto1.getProducto().getPrecio_mayorista()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.precio_minorista").value(discountDTOproducto1.getProducto().getPrecio_minorista()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.imageURL").value(discountDTOproducto1.getProducto().getImageURL()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.dimensiones").value(discountDTOproducto1.getProducto().getDimensiones()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.peso").value(discountDTOproducto1.getProducto().getPeso()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.material").value(discountDTOproducto1.getProducto().getMaterial()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.color").value(discountDTOproducto1.getProducto().getColor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.paisOrigen").value(discountDTOproducto1.getProducto().getPaisOrigen()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.activo").value(discountDTOproducto1.getProducto().getActivo()));
    }

    @Test
    void getDescuentoByNombreEndpointTest() throws Exception {
        String nombreExistente = "Descuento1";

        when(serviceDiscount.getDescuentoByNombre(nombreExistente)).thenReturn(ResponseEntity.ok(discountDTOproducto1));

        mockMvc.perform(get(API_PATH + "/name/{nombre}", nombreExistente)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(discountDTOproducto1.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(discountDTOproducto1.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(discountDTOproducto1.getDescripcion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.precio_min").value(discountDTOproducto1.getPrecio_min()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.activo").value(discountDTOproducto1.isActivo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.codigo").value(discountDTOproducto1.getProducto().getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.nombre").value(discountDTOproducto1.getProducto().getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.descripcion").value(discountDTOproducto1.getProducto().getDescripcion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.precio_compra").value(discountDTOproducto1.getProducto().getPrecio_compra()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.precio_mayorista").value(discountDTOproducto1.getProducto().getPrecio_mayorista()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.precio_minorista").value(discountDTOproducto1.getProducto().getPrecio_minorista()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.imageURL").value(discountDTOproducto1.getProducto().getImageURL()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.dimensiones").value(discountDTOproducto1.getProducto().getDimensiones()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.peso").value(discountDTOproducto1.getProducto().getPeso()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.material").value(discountDTOproducto1.getProducto().getMaterial()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.color").value(discountDTOproducto1.getProducto().getColor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.paisOrigen").value(discountDTOproducto1.getProducto().getPaisOrigen()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.activo").value(discountDTOproducto1.getProducto().getActivo()));
    }

    @Test
    void getAllDescuentosByProductEndpointTest() throws Exception {
        String codigoProducto = "P-CLA-001";

        when(serviceDiscount.getAllDescuentosByProductCode(codigoProducto)).thenReturn(ResponseEntity.ok(listDiscountsDTO));

        mockMvc.perform(get(API_PATH + "/product/{codigo}", codigoProducto)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(listDiscountsDTO.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty());
    }

    @Test
    void createDescuentoEndpointTest() throws Exception {
        DiscountDTOPost discountDTOPost = new DiscountDTOPost("D-DES-001", "Descuento1", true, "P-CLA-001");

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyJson = objectMapper.writeValueAsString(discountDTOPost);

        when(serviceDiscount.postDescuento(discountDTOPost)).thenReturn(ResponseEntity.ok(discountDTO1));

        mockMvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bodyJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(discountDTOproducto1.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(discountDTOproducto1.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(discountDTOproducto1.getDescripcion()));
    }

    @Test
    void eliminarDescuentoByCodeEndpointTest() throws Exception {
        String codigoDescuento = "D-DES-001";
        ResponseEntity<DiscountDTODelete> response = ResponseEntity.ok(new DiscountDTODelete(true, "Se ha inhabilitado el descuento con codigo: " + codigoDescuento));
        when(serviceDiscount.deleteDescuento(codigoDescuento)).thenReturn(response);

        mockMvc.perform(delete(API_PATH + "/{code}", codigoDescuento)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(response.getBody().isStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(response.getBody().getMessage()));
    }

    @Test
    void modificarDescuentoEndpointTest() throws Exception {

        DiscountDTOPut discountDTOPut = new DiscountDTOPut(discountDTO2.getNombre(), discountDTO2.getDescripcion(), discountDTO2.getCodigo_producto(), discountDTO2.isActivo());

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyJson = objectMapper.writeValueAsString(discountDTOPut);
        String descripcionNueva = "Descripción Descuento 2 Modificada";
        discountDTOPut.setDescripcion(descripcionNueva);
        discountDTO2.setDescripcion(descripcionNueva);

        when(serviceDiscount.putDescuento(discountDTOPut, discountDTO2.getCodigo()))
                .thenReturn(ResponseEntity.ok(discountDTOPut));

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put(API_PATH + "/{code}", discountDTO2.getCodigo())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(bodyJson);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }
}
