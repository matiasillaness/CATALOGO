package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPut;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BrandServiceTest {

    @Autowired
    private ServiceMarcaImpl serviceMarca;
    @Autowired
    ModelMapper modelMapper;

    @Transactional
    @Test
    public void obtenerMarcasTest() {
        serviceMarca.crearMarca(new MarcaDtoPost("STEEL")).getBody();

        serviceMarca.crearMarca(new MarcaDtoPost("KARCHER")).getBody();

        serviceMarca.crearMarca(new MarcaDtoPost("PHILLIPS")).getBody();

        serviceMarca.crearMarca(new MarcaDtoPost("BOSCH")).getBody();


        ResponseEntity<List<MarcaDto>> marcas = serviceMarca.obtenerMarcas();

        assertEquals(4, marcas.getBody().size());
        assertEquals(HttpStatusCode.valueOf(200), marcas.getStatusCode());

        assertEquals("STEEL", marcas.getBody().get(0).getNombre());
        assertEquals("M-STE-001", marcas.getBody().get(0).getCodigo());

        assertEquals("KARCHER", marcas.getBody().get(1).getNombre());
        assertEquals("M-KAR-001", marcas.getBody().get(1).getCodigo());
    }
    @Transactional
    @Test
    public void obtenerMarcaByCodeTest() {
        MarcaDto marcaDto = serviceMarca.crearMarca(new MarcaDtoPost("ACCORD")).getBody();

        ResponseEntity<MarcaDto> response = serviceMarca.obtenerMarcaByCode(marcaDto.getCodigo());

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        assertEquals("ACCORD", response.getBody().getNombre());
        assertEquals("M-ACC-001", response.getBody().getCodigo());
    }
    @Transactional
    @Test
    public void obtenerMarcaNombreTest() {
        MarcaDto marcaDto = serviceMarca.crearMarca(new MarcaDtoPost("PRULE")).getBody();

        ResponseEntity<MarcaDto> response = serviceMarca.obtenerMarcaNombre(marcaDto.getNombre());

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());

        assertEquals("PRULE", response.getBody().getNombre());
        assertEquals("M-PRU-001", response.getBody().getCodigo());
    }
    @Transactional
    @Test
    public void borrarMarcaTest() {
        MarcaDtoPost marcaDtoPost = new MarcaDtoPost();
        marcaDtoPost.setNombre("MITOS");
        ResponseEntity<MarcaDto> marcaResponse = serviceMarca.crearMarca(marcaDtoPost);


        ResponseEntity<Boolean> response = serviceMarca.borrarMarca(marcaResponse.getBody().getCodigo());
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertTrue(response.getBody().booleanValue());

    }
    @Transactional
    @Test
    public void crearMarcaTest() {
        MarcaDtoPost marcaDtoPost = new MarcaDtoPost();
        marcaDtoPost.setNombre("Tronos");
        ResponseEntity<MarcaDto> marcaResponse = serviceMarca.crearMarca(marcaDtoPost);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            serviceMarca.crearMarca(new MarcaDtoPost(""));
        });
        RuntimeException exception2 = assertThrows(RuntimeException.class, () -> {
            serviceMarca.crearMarca(marcaDtoPost);
        });
        RuntimeException exception3 = assertThrows(RuntimeException.class, () -> {
            String name = "asdasdasdasdasdasdasdasdsadsadsadasdsadsadasdsadasa";
            serviceMarca.crearMarca(new MarcaDtoPost(name));
        });

        assertEquals(HttpStatusCode.valueOf(200), marcaResponse.getStatusCode());
        assertEquals("TRONOS", marcaResponse.getBody().getNombre());
        assertEquals("M-TRO-001", marcaResponse.getBody().getCodigo());
        assertEquals("El nombre de la marca no puede estar vacío.", exception.getMessage());
        assertEquals("Ya existe una marca con ese nombre.", exception2.getMessage());
        assertEquals("El nombre de la marca no puede tener más de 50 caracteres.", exception3.getMessage());
    }
    @Transactional
    @Test
    public void modificarMarcaTest() {
        MarcaDtoPost marcaDtoPost = new MarcaDtoPost();
        marcaDtoPost.setNombre("Campos");
        ResponseEntity<MarcaDto> marca = serviceMarca.crearMarca(marcaDtoPost);

        MarcaDtoPut marcaDtoPut = new MarcaDtoPut("Campos2");

        ResponseEntity<MarcaDto> marcaResponseUpdate = serviceMarca.modificarMarca(marcaDtoPut, marca.getBody().getCodigo());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            serviceMarca.modificarMarca(new MarcaDtoPut(""), marca.getBody().getCodigo());
        });
        RuntimeException exception2 = assertThrows(RuntimeException.class, () -> {
            serviceMarca.modificarMarca(marcaDtoPut, marca.getBody().getCodigo());
        });
        RuntimeException exception3 = assertThrows(RuntimeException.class, () -> {
            String name = "asdasdasdasdasdasdasdasdsadsadsadasdsadsadasdsadasa";
            serviceMarca.modificarMarca(new MarcaDtoPut(name), marca.getBody().getCodigo());
        });

        assertEquals(HttpStatusCode.valueOf(200), marcaResponseUpdate.getStatusCode());
        assertEquals("CAMPOS2", marcaResponseUpdate.getBody().getNombre());
        assertEquals("M-CAM-001", marcaResponseUpdate.getBody().getCodigo());
        assertEquals("El nombre de la marca no puede estar vacío.", exception.getMessage());
        assertEquals("Ya existe una marca con ese nombre.", exception2.getMessage());
        assertEquals("El nombre de la marca no puede tener más de 50 caracteres.", exception3.getMessage());
    }
}
