package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPut;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.models.Categoria;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.repositories.CategoriaJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;


import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ServiceCategoriaTest {
    @Mock
    private CategoriaJpaRepository categoriaJpaRepository;
    @Autowired
    private ServiceCategoria serviceCategoria;
    @Autowired
    ModelMapper modelMapper;

    @Transactional
    @Test
    public void getAllCategoriasTest() {
        CategoriaDtoPost categoriaDtoPost = new CategoriaDtoPost();
        categoriaDtoPost.setNombre("Categoría 1");
        serviceCategoria.postCategoria(categoriaDtoPost);

        CategoriaDtoPost categoriaDtoPost2 = new CategoriaDtoPost();
        categoriaDtoPost2.setNombre("Categoría 2");
        serviceCategoria.postCategoria(categoriaDtoPost2);

        ResponseEntity<List<CategoriaDto>> listaDto = serviceCategoria.getAllCategorias();

        assertEquals(2, listaDto.getBody().size());
        assertEquals(HttpStatusCode.valueOf(200), listaDto.getStatusCode());

        assertEquals("CATEGORÍA 1", listaDto.getBody().get(0).getNombre());
        assertEquals("C-CAT-001", listaDto.getBody().get(0).getCodigo());

        assertEquals("CATEGORÍA 2", listaDto.getBody().get(1).getNombre());
        assertEquals("C-CAT-002", listaDto.getBody().get(1).getCodigo());
    }

    @Transactional
    @Test
    public void getCategoriaByCodigoTest() {
        CategoriaDtoPost categoriaDtoPost = new CategoriaDtoPost();
        categoriaDtoPost.setNombre("Categoría 1");
        ResponseEntity<CategoriaDto> categoriaDto = serviceCategoria.postCategoria(categoriaDtoPost);

        ResponseEntity<CategoriaDto> response = serviceCategoria.getCategoriaByCodigo(categoriaDto.getBody().getCodigo());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceCategoria.getCategoriaByCodigo("C-CAT-002");
        });

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("C-CAT-001", response.getBody().getCodigo());
        assertEquals("CATEGORÍA 1", response.getBody().getNombre());
        assertEquals("No se encontró una categoría con el código especificado.", exception.getMessage());
    }

    @Transactional
    @Test
    public void getCategoriaByNombreTest() {
        CategoriaDtoPost categoriaDtoPost = new CategoriaDtoPost();
        categoriaDtoPost.setNombre("Categoría 1");
        ResponseEntity<CategoriaDto> categoriaDto = serviceCategoria.postCategoria(categoriaDtoPost);

        ResponseEntity<CategoriaDto> response = serviceCategoria.getCategoriaByNombre(categoriaDto.getBody().getNombre());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceCategoria.getCategoriaByNombre("C-CAT-002");
        });

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("C-CAT-001", response.getBody().getCodigo());
        assertEquals("CATEGORÍA 1", response.getBody().getNombre());
        assertEquals("No se encontró una categoría con el nombre especificado.", exception.getMessage());
    }

    @Transactional
    @Test
    public void postCategoriaTest() {
        CategoriaDtoPost categoriaDtoPost = new CategoriaDtoPost();
        categoriaDtoPost.setNombre("Categoría 1");
        ResponseEntity<CategoriaDto> categoriaDto = serviceCategoria.postCategoria(categoriaDtoPost);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            serviceCategoria.postCategoria(new CategoriaDtoPost(""));
        });
        RuntimeException exception2 = assertThrows(RuntimeException.class, () -> {
            serviceCategoria.postCategoria(categoriaDtoPost);
        });
        RuntimeException exception3 = assertThrows(RuntimeException.class, () -> {
            String name = "asdasdasdasdasdasdasdasdsadsadsadasdsadsadasdsadasa";
            serviceCategoria.postCategoria(new CategoriaDtoPost(name));
        });

        assertEquals(HttpStatusCode.valueOf(200), categoriaDto.getStatusCode());
        assertEquals("CATEGORÍA 1", categoriaDto.getBody().getNombre());
        assertEquals("C-CAT-001", categoriaDto.getBody().getCodigo());
        assertEquals("El nombre de la categoría no puede estar vacío.", exception.getMessage());
        assertEquals("Ya existe una categoría con ese nombre.", exception2.getMessage());
        assertEquals("El nombre de la categoría no puede tener más de 50 caracteres.", exception3.getMessage());
    }

    @Transactional
    @Test
    public void deleteCategoriaTest() {
        CategoriaDtoPost categoriaDtoPost = new CategoriaDtoPost();
        categoriaDtoPost.setNombre("Categoría 1");
        ResponseEntity<CategoriaDto> categoriaDto = serviceCategoria.postCategoria(categoriaDtoPost);


        ResponseEntity<Boolean> response = serviceCategoria.deleteCategoria(categoriaDto.getBody().getCodigo());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().booleanValue());

    }

    @Transactional
    @Test
    public void putCategoriaTest() {
        CategoriaDtoPost categoriaDtoPost = new CategoriaDtoPost();
        categoriaDtoPost.setNombre("Categoría 1");
        ResponseEntity<CategoriaDto> categoriaDto = serviceCategoria.postCategoria(categoriaDtoPost);

        CategoriaDtoPut categoria = new CategoriaDtoPut();
        categoria.setNombre("Categoría 2");

        Categoria categoria1 = new Categoria();
        categoria1.setCodigo("C-CAT-002");
        categoria1.setNombre("Categoría 2");

        ResponseEntity<CategoriaDto> response = serviceCategoria.putCategoria(categoria, categoriaDto.getBody().getCodigo());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            serviceCategoria.putCategoria(categoria, "C-CAT-002");
        });
        EntityNotFoundException exception1 = assertThrows(EntityNotFoundException.class, () -> {
            serviceCategoria.putCategoria(new CategoriaDtoPut(""),"C-CAT-001");
        });
        EntityNotFoundException exception2 = assertThrows(EntityNotFoundException.class, () -> {
            serviceCategoria.putCategoria(new CategoriaDtoPut("Categoría 2"),"C-CAT-001");
        });
        EntityNotFoundException exception3 = assertThrows(EntityNotFoundException.class, () -> {
            String name = "asdasdasdasdasdasdasdasdsadsadsadasdsadsadasdsadasa";
            serviceCategoria.putCategoria(new CategoriaDtoPut(name),"C-CAT-001");
        });



        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals("C-CAT-001", response.getBody().getCodigo());
        assertEquals("CATEGORÍA 2", response.getBody().getNombre());


        assertEquals("No se encontró una categoría con el código especificado.", exception.getMessage());
        assertEquals("El nombre de la categoría no puede estar vacío.", exception1.getMessage());
        assertEquals("Ya existe una categoría con ese nombre.", exception2.getMessage());
        assertEquals("El nombre de la categoría no puede tener más de 50 caracteres.", exception3.getMessage());
    }
}
