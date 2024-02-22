package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.controllers;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPut;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.services.ServiceCategoria;
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

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ServiceCategoria serviceCategoria;

    private final String API_PATH = "/api/categories";

    List<CategoriaDto> listCategoriesDTO;
    CategoriaDto categoriaDto,categoriaDto2,categoriaDto3,categoriaDto4;
    @BeforeEach
    public void setup() {
        listCategoriesDTO = new ArrayList<>();
        categoriaDto = new CategoriaDto("C-FER-001","FERRETERIA");

        categoriaDto2 = new CategoriaDto("C-PIN-001","PINTURAS");

        categoriaDto3 = new CategoriaDto("C-HER-001","HERRAMIENTAS");

        categoriaDto4 = new CategoriaDto("C-HER-002","HERRERIA");


        listCategoriesDTO.add(categoriaDto);
        listCategoriesDTO.add(categoriaDto2);
        listCategoriesDTO.add(categoriaDto3);
        listCategoriesDTO.add(categoriaDto4);
    }

    @Test
    public void getAllCategoriasTest() throws Exception {

        when(serviceCategoria.getAllCategorias()).thenReturn(ResponseEntity.ok(listCategoriesDTO));

        mockMvc.perform(get(API_PATH).contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(listCategoriesDTO.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty());

    }

    @Test
    public void getCategoriaByCodigoTest() throws Exception {
        String codigoExistente = "C-FER-001";

        when(serviceCategoria.getCategoriaByCodigo(codigoExistente)).thenReturn(ResponseEntity.ok(categoriaDto));

        mockMvc.perform(get(API_PATH+"/{code}", codigoExistente)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(categoriaDto.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(categoriaDto.getNombre()));
    }

    @Test
    public void getCategoriaByNombreTest() throws Exception {
        String nombreExistente = "HERRERIA";

        when(serviceCategoria.getCategoriaByNombre(nombreExistente)).thenReturn(ResponseEntity.ok(categoriaDto4));

        mockMvc.perform(get(API_PATH+"/name/{nombre}", nombreExistente)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(categoriaDto4.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(categoriaDto4.getNombre()));
    }

    @Test
    void createCategoriaEndpointTest() throws Exception {
        CategoriaDtoPost categoriaDtoPost = new CategoriaDtoPost();
        categoriaDtoPost.setNombre("HERRAMIENTAS");

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyJson = objectMapper.writeValueAsString(categoriaDtoPost);

        when(serviceCategoria.postCategoria(categoriaDtoPost)).thenReturn(ResponseEntity.ok(categoriaDto3));

        mockMvc.perform(post(API_PATH)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(bodyJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.codigo").value(categoriaDto3.getCodigo()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nombre").value(categoriaDto3.getNombre()));
    }

    @Test
    void eliminarCategoriasPorCodigoEndpointTest() throws Exception {
        String codigoCategoria = "C-PIN-001";

        when(serviceCategoria.deleteCategoria(codigoCategoria)).thenReturn(ResponseEntity.ok(true));

        mockMvc.perform(delete(API_PATH+"/{code}", codigoCategoria)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").value(true));

    }

    @Test
    void modificarCategoriaEndpointTest() throws Exception {
        CategoriaDto catDTO = categoriaDto2;
        CategoriaDtoPut categoriaDtoPut = new CategoriaDtoPut(catDTO.getNombre());

        ObjectMapper objectMapper = new ObjectMapper();
        String bodyJson = objectMapper.writeValueAsString(categoriaDtoPut);
        String nombreNuevo = "PINTURAS2";
        categoriaDtoPut.setNombre(nombreNuevo);

        when(serviceCategoria.putCategoria(categoriaDtoPut,catDTO.getCodigo()))
                .thenReturn(ResponseEntity.ok(catDTO));

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put(API_PATH +"/{codigo}",catDTO.getCodigo())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(bodyJson);


        mockMvc.perform(builder)
                .andExpect(status().isOk());

    }
}
