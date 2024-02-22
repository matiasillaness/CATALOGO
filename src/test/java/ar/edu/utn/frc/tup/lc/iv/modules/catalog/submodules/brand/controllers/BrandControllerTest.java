package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.controllers;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPut;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.services.ServiceMarcaImpl;
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

@WebMvcTest(MarcaController.class)
public class BrandControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServiceMarcaImpl serviceMarca;

    private final String API_PATH = "/api/brands";

    List<MarcaDto> listBrandsDTO;
    MarcaDto marcaDto, marcaDto1, marcaDto2, marcaDto3;

    @BeforeEach
    public void setup() {
        listBrandsDTO = new ArrayList<>();
        marcaDto = new MarcaDto("M-BOS-001","BOSCH");

        marcaDto1 = new MarcaDto("M-PHI-001","PHILLIPS");

        marcaDto2 = new MarcaDto("M-KAR-001","KARCHER");

        marcaDto3 = new MarcaDto("M-STE-001","STEEL");


        listBrandsDTO.add(marcaDto);
        listBrandsDTO.add(marcaDto1);
        listBrandsDTO.add(marcaDto2);
        listBrandsDTO.add(marcaDto3);
    }

    @Test
    public void getAllCategoriasTest() throws Exception {

        when(serviceMarca.obtenerMarcas()).thenReturn(ResponseEntity.ok(listBrandsDTO));

        mockMvc.perform(get(API_PATH).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(listBrandsDTO.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty());

    }

    @Test
    public void getCategoriaByCodigoTest() throws Exception {
        String codigoExistente = "M-BOS-001";

        when(serviceMarca.obtenerMarcaByCode(codigoExistente)).thenReturn(ResponseEntity.ok(marcaDto));

        mockMvc.perform(get(API_PATH+"/{code}", codigoExistente)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(marcaDto.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(marcaDto.getNombre()));
    }

    @Test
    public void getCategoriaByNombreTest() throws Exception {
        String nombreExistente = "STEEL";

        when(serviceMarca.obtenerMarcaNombre(nombreExistente)).thenReturn(ResponseEntity.ok(marcaDto3));

        mockMvc.perform(get(API_PATH+"/name/{nombre}", nombreExistente)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(marcaDto3.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(marcaDto3.getNombre()));
    }

    @Test
    void createCategoriaEndpointTest() throws Exception {
        MarcaDtoPost marcaDtoPost = new MarcaDtoPost();
        marcaDtoPost.setNombre("HERRAMIENTAS");

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyJson = objectMapper.writeValueAsString(marcaDtoPost);

        when(serviceMarca.crearMarca(marcaDtoPost)).thenReturn(ResponseEntity.ok(marcaDto2));

        mockMvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bodyJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(marcaDto2.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(marcaDto2.getNombre()));
    }

    @Test
    void eliminarCategoriasPorCodigoEndpointTest() throws Exception {
        String codigoCategoria = "M-KAR-001";

        when(serviceMarca.borrarMarca(codigoCategoria)).thenReturn(ResponseEntity.ok(true));

        mockMvc.perform(delete(API_PATH+"/{code}", codigoCategoria)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

    }

    @Test
    void modificarCategoriaEndpointTest() throws Exception {
        MarcaDto brandDTO = marcaDto1;
        MarcaDtoPut marcaDtoPut = new MarcaDtoPut(brandDTO.getNombre());

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyJson = objectMapper.writeValueAsString(marcaDtoPut);
        String nombreNuevo = "PHILLIPS2";
        marcaDtoPut.setNombre(nombreNuevo);

        when(serviceMarca.modificarMarca(marcaDtoPut,brandDTO.getCodigo()))
                .thenReturn(ResponseEntity.ok(brandDTO));

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put(API_PATH +"/{codigo}",brandDTO.getCodigo())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(bodyJson);


        mockMvc.perform(builder)
                .andExpect(status().isOk());

    }
}
