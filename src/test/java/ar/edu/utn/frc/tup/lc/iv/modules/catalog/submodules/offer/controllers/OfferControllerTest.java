package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.controllers;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos.*;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.dtos.*;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.services.ServiceOferta;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTODescuento;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTOOferta;
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

@WebMvcTest(OfferController.class)
public class OfferControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServiceOferta serviceOferta;
    private final String API_PATH = "/api/offers";
    List<OfferDTOProducto> listOffersDTOproducto;
    OfferDTOProducto offerDTOProducto1, offerDTOProducto2, offerDTOProducto3, offerDTOProducto4;
    List<OfferDTO> listOffersDTO;
    OfferDTO offerDTO1, offerDTO2, offerDTO3, offerDTO4;

    @BeforeEach
    public void setup() {
        listOffersDTOproducto = new ArrayList<>();
        listOffersDTO = new ArrayList<>();

        ProductoDTOOferta productoDTO1 = new ProductoDTOOferta("P-CLA-001", "Clavos de Acero",
                "Clavos de Acero de dimensiones 10x5x3 y peso de 0.5kg", "100", "110", "120",
                "https://centrosider.com.ar/wp-content/uploads/2020/08/clavos-punta-paris-acindar-1.jpg", "10x5x3 cm",
                "0.5 kg", "Acero", "Plateado", "Argentina", true);

        ProductoDTOOferta productoDTO2 = new ProductoDTOOferta("P-LAM-001", "Lámpara LED",
                "Lámpara LED de 15W con luz blanca", "200", "220", "250",
                "https://example.com/lampara-led.jpg", "10x10x15 cm",
                "0.5 kg", "Plástico", "Blanco", "China", true);

        ProductoDTOOferta productoDTO3 = new ProductoDTOOferta("P-MAR-001", "Martillo de Hierro",
                "Martillo de Hierro de dimensiones 15x7x5 y peso de 1kg", "150", "160", "180",
                "https://example.com/martillo.jpg", "15x7x5 cm",
                "1 kg", "Hierro", "Negro", "Argentina", true);

        ProductoDTOOferta productoDTO4 = new ProductoDTOOferta("P-TUB-001", "Tubo de PVC",
                "Tubo de PVC de 2 metros de longitud y diámetro de 10cm", "50", "60", "70",
                "https://example.com/tubo-pvc.jpg", "200x10 cm",
                "1 kg", "PVC", "Blanco", "Argentina", false);

        offerDTO1 = new OfferDTO("O-OFE-001", "Oferta1", "Descripción Oferta 1", BigDecimal.valueOf(1000), BigDecimal.valueOf(11), true, "P-CLA-001");
        offerDTO2 = new OfferDTO("O-OFE-002", "Oferta 2", "Descripción Oferta 2", BigDecimal.valueOf(2000), BigDecimal.valueOf(22), true, "P-LAM-001");
        offerDTOProducto1 = new OfferDTOProducto("O-OFE-001", "Oferta1", "Descripción Oferta 1", BigDecimal.valueOf(1000), BigDecimal.valueOf(11), true, productoDTO1);
        offerDTOProducto2 = new OfferDTOProducto("O-OFE-002", "Oferta 2", "Descripción Oferta 2", BigDecimal.valueOf(2000), BigDecimal.valueOf(22), true, productoDTO2);
        offerDTOProducto3 = new OfferDTOProducto("O-OFE-003", "Oferta 3", "Descripción Oferta 3", BigDecimal.valueOf(3000), BigDecimal.valueOf(33), true, productoDTO3);
        offerDTOProducto4 = new OfferDTOProducto("O-OFE-004", "Oferta 4", "Descripción Oferta 4", BigDecimal.valueOf(4000), BigDecimal.valueOf(44), true, productoDTO4);

        listOffersDTO.add(offerDTO1);
        listOffersDTO.add(offerDTO2);
        listOffersDTOproducto.add(offerDTOProducto1);
        listOffersDTOproducto.add(offerDTOProducto2);
        listOffersDTOproducto.add(offerDTOProducto3);
        listOffersDTOproducto.add(offerDTOProducto4);
    }


    @Test
    void getAllDescuentosEndpointTest() throws Exception {
        when(serviceOferta.getAllOfertas()).thenReturn(ResponseEntity.ok(listOffersDTOproducto));

        mockMvc.perform(get(API_PATH).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(listOffersDTOproducto.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty());
    }

    @Test
    void getDescuentoByCodigoEndpointTest() throws Exception {
        String codigoExistente = "O-OFE-001";

        when(serviceOferta.getOfertaByCodigo(codigoExistente)).thenReturn(ResponseEntity.ok(offerDTOProducto1));

        mockMvc.perform(get(API_PATH + "/{codigo}", codigoExistente)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(offerDTOProducto1.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(offerDTOProducto1.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(offerDTOProducto1.getDescripcion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.precio_oferta").value(offerDTOProducto1.getPrecio_oferta()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.activo").value(offerDTOProducto1.isActivo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.codigo").value(offerDTOProducto1.getProducto().getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.nombre").value(offerDTOProducto1.getProducto().getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.descripcion").value(offerDTOProducto1.getProducto().getDescripcion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.precio_compra").value(offerDTOProducto1.getProducto().getPrecio_compra()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.precio_mayorista").value(offerDTOProducto1.getProducto().getPrecio_mayorista()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.precio_minorista").value(offerDTOProducto1.getProducto().getPrecio_minorista()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.imageURL").value(offerDTOProducto1.getProducto().getImageURL()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.dimensiones").value(offerDTOProducto1.getProducto().getDimensiones()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.peso").value(offerDTOProducto1.getProducto().getPeso()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.material").value(offerDTOProducto1.getProducto().getMaterial()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.color").value(offerDTOProducto1.getProducto().getColor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.paisOrigen").value(offerDTOProducto1.getProducto().getPaisOrigen()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.activo").value(offerDTOProducto1.getProducto().getActivo()));
    }

    @Test
    void getDescuentoByNombreEndpointTest() throws Exception {
        String nombreExistente = "Oferta1";

        when(serviceOferta.getOfertaByNombre(nombreExistente)).thenReturn(ResponseEntity.ok(offerDTOProducto1));

        mockMvc.perform(get(API_PATH + "/name/{nombre}", nombreExistente)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(offerDTOProducto1.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(offerDTOProducto1.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(offerDTOProducto1.getDescripcion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.precio_oferta").value(offerDTOProducto1.getPrecio_oferta()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.activo").value(offerDTOProducto1.isActivo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.codigo").value(offerDTOProducto1.getProducto().getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.nombre").value(offerDTOProducto1.getProducto().getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.descripcion").value(offerDTOProducto1.getProducto().getDescripcion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.precio_compra").value(offerDTOProducto1.getProducto().getPrecio_compra()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.precio_mayorista").value(offerDTOProducto1.getProducto().getPrecio_mayorista()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.precio_minorista").value(offerDTOProducto1.getProducto().getPrecio_minorista()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.imageURL").value(offerDTOProducto1.getProducto().getImageURL()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.dimensiones").value(offerDTOProducto1.getProducto().getDimensiones()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.peso").value(offerDTOProducto1.getProducto().getPeso()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.material").value(offerDTOProducto1.getProducto().getMaterial()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.color").value(offerDTOProducto1.getProducto().getColor()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.paisOrigen").value(offerDTOProducto1.getProducto().getPaisOrigen()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.producto.activo").value(offerDTOProducto1.getProducto().getActivo()));
    }

    @Test
    void getAllDescuentosByProductEndpointTest() throws Exception {
        String codigoProducto = "P-CLA-001";

        when(serviceOferta.getAllOfertasByProductCode(codigoProducto)).thenReturn(ResponseEntity.ok(listOffersDTO));

        mockMvc.perform(get(API_PATH + "/product/{codigo}", codigoProducto)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(listOffersDTO.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty());
    }

    @Test
    void createDescuentoEndpointTest() throws Exception {
        OfferDTOPost offerDTOPost = new OfferDTOPost("O-OFE-001", "Oferta1", BigDecimal.valueOf(1000), true, "P-CLA-001");

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyJson = objectMapper.writeValueAsString(offerDTOPost);

        when(serviceOferta.postOferta(offerDTOPost)).thenReturn(ResponseEntity.ok(offerDTO1));

        mockMvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bodyJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(offerDTOProducto1.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(offerDTOProducto1.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(offerDTOProducto1.getDescripcion()));
    }

    @Test
    void eliminarDescuentoByCodeEndpointTest() throws Exception {
        String codigoOferta = "O-OFE-001";
        ResponseEntity<OfferDTODelete> response = ResponseEntity.ok(new OfferDTODelete(true, "Se ha inhabilitado la oferta con codigo: " + codigoOferta));
        when(serviceOferta.deleteOferta(codigoOferta)).thenReturn(response);

        mockMvc.perform(delete(API_PATH + "/{code}", codigoOferta)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(response.getBody().isStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(response.getBody().getMessage()));
    }

    @Test
    void modificarDescuentoEndpointTest() throws Exception {

        OfferDTOPut offerDTOPut = new OfferDTOPut(offerDTO2.getNombre(), offerDTO2.getDescripcion(), offerDTO2.getPrecio_oferta(), offerDTO2.isActivo(), offerDTO2.getCodigo_producto());

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyJson = objectMapper.writeValueAsString(offerDTOPut);
        String descripcionNueva = "Descripción Oferta 2 Modificada";
        offerDTOPut.setDescripcion(descripcionNueva);
        offerDTO2.setDescripcion(descripcionNueva);

        when(serviceOferta.putOferta(offerDTOPut, offerDTO2.getCodigo()))
                .thenReturn(ResponseEntity.ok(offerDTOPut));

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put(API_PATH + "/{code}", offerDTO2.getCodigo())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(bodyJson);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }
}
