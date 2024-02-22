package ar.edu.utn.frc.tup.lc.iv.modules.catalog.services;

import ar.edu.utn.frc.tup.lc.iv.common.services.ServiceCommon;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.create.CatalogCreateDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.common.CatalogResponseDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.update.UpdateCatalogDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.entities.CatalogoEntity;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.repositories.CatalogJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.entities.ProductoEntity;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.repositories.ProductoJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceCatalog implements  IServiceCatalog {

    @Autowired
    CatalogJpaRepository catalogJpaRepository;
    @Autowired
    ServiceCommon serviceCommon;
    @Autowired
    ProductoJpaRepository productoJpaRepository;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public ResponseEntity<CatalogResponseDTO> postCatalog(CatalogCreateDTO newCatalogo) {

        newCatalogo.setNombre(newCatalogo.getNombre().toUpperCase());
        List<ProductoEntity> productsList = new ArrayList<>();

        CatalogoEntity catalogEntity = new CatalogoEntity(null, newCatalogo.getNombre().toUpperCase(), null, newCatalogo.getDescripcion().toUpperCase(), null);
        if (catalogJpaRepository.getByNombre(newCatalogo.getNombre()) != null) {
            throw new RuntimeException("Ya existe un catalogo con ese nombre.");
        }
        if (newCatalogo.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del catalogo no puede estar vacío.");
        }
        if (newCatalogo.getNombre().length() > 50) {
            throw new RuntimeException("El nombre del catalogo no puede tener más de 50 caracteres.");
        }
        catalogEntity = catalogJpaRepository.save(catalogEntity);

        if (newCatalogo.getProductos_asociados() != null) {
            for (String productCode : newCatalogo.getProductos_asociados()) {
                productCode = productCode.toUpperCase();
                ProductoEntity productEntity = productoJpaRepository.getByCodigo(productCode);
                if (productEntity == null) {
                    throw new EntityNotFoundException(String.format("El producto con codigo '" + productCode + "' no existe."));
                }
                productsList.add(productEntity);
            }
        }

        if (serviceCommon.createCode(newCatalogo) != null) {
            catalogEntity.setCodigo(serviceCommon.createCode(newCatalogo));
        }

        catalogEntity.setProductos_asociados(productsList);
        catalogJpaRepository.save(catalogEntity);
        CatalogResponseDTO catalogResponseDTO = modelMapper.map(catalogEntity, CatalogResponseDTO.class);
        catalogResponseDTO.setProductos_asociados(mapearListaProductoDTOCatalogo(catalogEntity.getProductos_asociados()));

        return ResponseEntity.ok(catalogResponseDTO);

    }

    @Override
    public ResponseEntity<List<CatalogResponseDTO>> getAllCatalogs() {
        List<CatalogoEntity> catalogsEntity = catalogJpaRepository.findAll();
        List<CatalogResponseDTO> catalogsDTOs = new ArrayList<>();

        catalogsEntity.forEach(entity -> {
            CatalogResponseDTO catalogResponseDTO = modelMapper.map(entity, CatalogResponseDTO.class);
            catalogResponseDTO.setProductos_asociados(mapearListaProductoDTOCatalogo(entity.getProductos_asociados()));
            catalogsDTOs.add(catalogResponseDTO);
        });

        return ResponseEntity.ok(catalogsDTOs);
    }

    @Override
    public ResponseEntity<CatalogResponseDTO> getCatalogByCode(String code) {
        CatalogoEntity catalogoEntity = catalogJpaRepository.getByCodigo(code);
        if (catalogoEntity == null) {
            throw new EntityNotFoundException(String.format("El catálogo con codigo " + code + " no existe."));
        }

        CatalogResponseDTO catalogResponseDTO = modelMapper.map(catalogoEntity, CatalogResponseDTO.class);
        catalogResponseDTO.setProductos_asociados(mapearListaProductoDTOCatalogo(catalogoEntity.getProductos_asociados()));

        return ResponseEntity.ok(catalogResponseDTO);
    }

    @Override
    @Transactional
    public ResponseEntity<Boolean> deleteCatalogByCode(String code) {
        CatalogoEntity catalogoEntity = catalogJpaRepository.getByCodigo(code);
        if (catalogoEntity == null) {
            throw new EntityNotFoundException(String.format("El catálogo con codigo " + code + " no existe."));
        }

        catalogoEntity.getProductos_asociados().clear();
        catalogJpaRepository.save(catalogoEntity);
        catalogJpaRepository.deleteByCodigo(code);

        return ResponseEntity.status(200).body(true);
    }



    @Override
    @Transactional
    public ResponseEntity<CatalogResponseDTO> updateCatalogByCode(String code, UpdateCatalogDTO payload) {
        payload.setNombre(payload.getNombre().toUpperCase());
        CatalogoEntity catalogoEntity = catalogJpaRepository.getByCodigo(code);
        if (catalogoEntity == null) {
            throw new EntityNotFoundException(String.format("El catálogo con codigo " + code + " no existe."));
        }
        if (catalogJpaRepository.getByNombre(payload.getNombre()) != null) {
            throw new RuntimeException("Ya existe un catalogo con ese nombre.");
        }
        if (payload.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del catalogo no puede estar vacío.");
        }
        if (payload.getNombre().length() > 50) {
            throw new RuntimeException("El nombre del catalogo no puede tener más de 50 caracteres.");
        }

        catalogoEntity.setNombre(payload.getNombre().toUpperCase());
        if (payload.getDescripcion() != null) {
            catalogoEntity.setDescripcion(payload.getDescripcion().toUpperCase());
        }
        catalogoEntity.setProductos_asociados(new ArrayList<>());
        for (String productCode : payload.getProductos_asociados()) {
            productCode = productCode.toUpperCase();
            ProductoEntity productEntity = productoJpaRepository.getByCodigo(productCode);
            if (productEntity == null) {
                throw new EntityNotFoundException(String.format("El producto con codigo '" + productCode + "' no existe."));
            }

            if (!catalogoEntity.getProductos_asociados().contains(productEntity)) {
                catalogoEntity.addProduct(productEntity);
            }
        }

        catalogJpaRepository.save(catalogoEntity);

        CatalogResponseDTO catalogResponseDTO = modelMapper.map(catalogoEntity, CatalogResponseDTO.class);
        catalogResponseDTO.setProductos_asociados(mapearListaProductoDTOCatalogo(catalogoEntity.getProductos_asociados()));

        return ResponseEntity.ok(catalogResponseDTO);

    }


    private List<ProductoDTO> mapearListaProductoDTOCatalogo(List<ProductoEntity> entityList){

        List<ProductoDTO> listaDTO = new ArrayList<>();
        for (ProductoEntity productoEntity : entityList) {
            ProductoDTO productoDTO = modelMapper.map(productoEntity,ProductoDTO.class);
            productoDTO.setCategorias(new ArrayList<>());
            serviceCommon.mapearCategoriasToProducts(productoDTO,productoDTO.getCategorias(),productoEntity);

            productoDTO.setDescuentos(serviceCommon.getAllDescuentosByProductCode(productoDTO.getCodigo()));

            productoDTO.setOfertas(serviceCommon.getAllOfertasByProductCode(productoDTO.getCodigo()));

            listaDTO.add(productoDTO);
        }


        return listaDTO;
    }



}
