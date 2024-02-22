package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.catalog.controllers;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.controllers.CatalogController;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.common.CatalogResponseDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.create.CatalogCreateDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.update.UpdateCatalogDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.services.ServiceCatalog;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.models.Marca;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTO;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CatalogController.class)
public class CatalogControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceCatalog serviceCatalog;

    private final String API_PATH = "/api/catalogs";
    List<CatalogResponseDTO> listCatalogs;
    CatalogResponseDTO catalogDTO,catalogDTO2,catalogDTO3,catalogDTO4;

    @BeforeEach
    public void setup() {
        listCatalogs = new ArrayList<>();
        ProductoDTO productoDTO = new ProductoDTO("P-CLA-001","Clavos de Acero","Clavos de Acero de dimensiones 10x5x3 y peso de 0.5kg","100",
                "110","120","https://centrosider.com.ar/wp-content/uploads/2020/08/clavos-punta-paris-acindar-1.jpg","10x5x3 cm",
                "0.5 kg","Acero","Plateado","Argentina",new Marca("M-STE-001","STEEL"),
                List.of(new CategoriaDto("C-CLA-001","CLAVOS"), new CategoriaDto("C-FER-001","FERRETERIA")),null,null,true);

        ProductoDTO productoDTO2 = new ProductoDTO("P-LAM-001", "Lámpara LED", "Lámpara LED de 15W con luz blanca",
                "200", "220", "250",
                "https://example.com/lampara-led.jpg", "10x10x15 cm",
                "0.5 kg", "Plástico", "Blanco", "China", new Marca("M-LED-001", "LED-MARCA"),
                List.of(new CategoriaDto("C-LAM-001", "ILUMINACION"),new CategoriaDto("C-TUB-001", "TUBERIAS")), null, null, true);

        ProductoDTO productoDTO3 = new ProductoDTO("P-MAR-001", "Martillo de Hierro", "Martillo de Hierro de dimensiones 15x7x5 y peso de 1kg",
                "150", "160", "180",
                "https://example.com/martillo.jpg", "15x7x5 cm",
                "1 kg", "Hierro", "Negro", "Argentina", new Marca("M-STE-001", "STEEL"),
                List.of(new CategoriaDto("C-HER-001", "HERRAMIENTAS")), null, null, true);

        ProductoDTO productoDTO4 = new ProductoDTO("P-TUB-001", "Tubo de PVC", "Tubo de PVC de 2 metros de longitud y diámetro de 10cm",
                "50", "60", "70",
                "https://example.com/tubo-pvc.jpg", "200x10 cm",
                "1 kg", "PVC", "Blanco", "Argentina", new Marca("M-PVC-001", "PVC-MARCA"),
                List.of(new CategoriaDto("C-TUB-001", "TUBERIAS"),new CategoriaDto("C-LAM-001", "ILUMINACION")), null, null, false);




        catalogDTO = new CatalogResponseDTO("CA-PRI-001","PRINCIPAL","Catalogo que contiene todos los productos",List.of(productoDTO,productoDTO2,productoDTO3,productoDTO4));

        catalogDTO2 = new CatalogResponseDTO("CA-STE-001","STEEL","Catalogo de la marca STEEL",List.of(productoDTO,productoDTO3));

        catalogDTO3 = new CatalogResponseDTO();
        catalogDTO3.setCodigo("CA-ILU-001");
        catalogDTO3.setNombre("ILUMINACION");
        catalogDTO3.setDescripcion("Productos de iluminacion");
        catalogDTO3.setProductos_asociados(List.of(productoDTO2));

        catalogDTO4 = new CatalogResponseDTO("CA-ARG-002","ARGENTINOS","Productos de origen argentino",List.of(productoDTO,productoDTO3,productoDTO4));


        listCatalogs.add(catalogDTO);
        listCatalogs.add(catalogDTO2);
        listCatalogs.add(catalogDTO3);
        listCatalogs.add(catalogDTO4);

    }


    @Test
    void getAllCatalogsEndpointTest() throws Exception {
        when(serviceCatalog.getAllCatalogs()).thenReturn(ResponseEntity.ok(listCatalogs));

        mockMvc.perform(get(API_PATH).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(listCatalogs.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty());
    }

    @Test
    void getCatalogByCodeEndpointTest() throws Exception {
        String codigoExistente = "CA-PRI-001";

        when(serviceCatalog.getCatalogByCode(codigoExistente)).thenReturn(ResponseEntity.ok(catalogDTO));

        mockMvc.perform(get(API_PATH+"/{code}", codigoExistente)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(catalogDTO.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(catalogDTO.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(catalogDTO.getDescripcion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productos_asociados.size()").value(catalogDTO.getProductos_asociados().size()));

    }

    @Test
    void createCatalogEndpointTest() throws Exception {
        CatalogCreateDTO catalogCreateDTO = new CatalogCreateDTO("STEEL","Catalogo de la marca STEEL",List.of("P-MAR-001","P-CLA-001"));

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyJson = objectMapper.writeValueAsString(catalogCreateDTO);

        when(serviceCatalog.postCatalog(catalogCreateDTO)).thenReturn(ResponseEntity.ok(catalogDTO2));

        mockMvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bodyJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(catalogDTO2.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(catalogDTO2.getNombre()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.descripcion").value(catalogDTO2.getDescripcion()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productos_asociados.size()").value(catalogDTO2.getProductos_asociados().size()));
    }

    @Test
    void deleteCatalogByCodeEndpointTest() throws Exception {
        String codigoProducto = "CA-ILU-001";

        ResponseEntity<Boolean> response = ResponseEntity.ok(true);
        when(serviceCatalog.deleteCatalogByCode(codigoProducto)).thenReturn(response);

        mockMvc.perform(delete(API_PATH+"/{code}", codigoProducto)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));
    }

    @Test
    void updateCatalogEndpointTest() throws Exception {
        CatalogResponseDTO catalogResponseDTO = catalogDTO3;

        UpdateCatalogDTO updateCatalogDTO = new UpdateCatalogDTO(catalogDTO3.getDescripcion(),catalogDTO3.getNombre(),List.of("P-LAM-001"));

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyJson = objectMapper.writeValueAsString(updateCatalogDTO);
        String descripcionNueva = "Productos de Iluminacion";
        updateCatalogDTO.setDescripcion(descripcionNueva);
        catalogResponseDTO.setDescripcion(descripcionNueva);

        when(serviceCatalog.updateCatalogByCode(catalogResponseDTO.getCodigo(),updateCatalogDTO))
                .thenReturn(ResponseEntity.ok(catalogResponseDTO));

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put(API_PATH + "/{code}",catalogResponseDTO.getCodigo())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(bodyJson);

        mockMvc.perform(builder)
                .andExpect(status().isOk());
    }


}
