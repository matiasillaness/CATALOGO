package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.services;


import ar.edu.utn.frc.tup.lc.iv.common.services.ServiceCommon;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.repositories.MarcaJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.entities.CategoriaEntity;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.repositories.CategoriaJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.repositories.DiscountJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.repositories.OfferJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTODelete;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDtoCompras;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.entities.ProductoEntity;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.Producto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.ProductoPreCarga;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.repositories.ProductoJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ServiceProducto implements iServiceProducto {

    @Autowired
    ProductoJpaRepository productoJpaRepository;

    @Autowired
    OfferJpaRepository offerJpaRepository;

    @Autowired
    DiscountJpaRepository discountJpaRepository;

    @Autowired
    MarcaJpaRepository marcaJpaRepository;

    @Autowired
    CategoriaJpaRepository categoriaJpaRepository;

    @Autowired
    ServiceCommon serviceCommon;

    @Autowired
    ModelMapper modelMapper;

    final BigDecimal PORCENTAJE_MINORISTA = new BigDecimal("1.4"); //40% de Ganancia
    final BigDecimal PORCENTAJE_MAYORISTA = new BigDecimal("1.3"); //30% de Ganancia
    @Override
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        List<ProductoEntity> lista = productoJpaRepository.findAll();
        return getListResponseEntity(lista);
    }

    @Override
    public ResponseEntity<ProductoDTO> getProductoByCode(String code) {
        ProductoDTO prodMapped = new ProductoDTO();
        List<CategoriaDto> catMapped = new ArrayList<>();
        if(productoJpaRepository.getByCodigo(code) == null) {
            throw new EntityNotFoundException(String.format("El producto con codigo "+ code +" no existe."));
        }
        ProductoEntity productoEntity = productoJpaRepository.getByCodigo(code);

        serviceCommon.mapearCategoriasToProducts(prodMapped, catMapped, productoEntity);
        prodMapped.setDescuentos(serviceCommon.getAllDescuentosByProductCode(productoEntity.getCodigo()));
        prodMapped.setOfertas(serviceCommon.getAllOfertasByProductCode(productoEntity.getCodigo()));


        return ResponseEntity.ok(prodMapped);
    }




    @Override
    public ResponseEntity<ProductoDtoCompras> createProducto(ProductoPreCarga newProducto) {
        newProducto.setNombre(newProducto.getNombre().toUpperCase());
        ProductoEntity productoEntity = modelMapper.map(newProducto, ProductoEntity.class);

        if (productoJpaRepository.getByNombre(newProducto.getNombre()) != null) {
            throw new RuntimeException("Ya existe un producto con ese nombre.");
        }
        if (newProducto.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del producto no puede estar vacío.");
        }
        if (newProducto.getNombre().length() > 50) {
            throw new RuntimeException("El nombre del producto no puede tener más de 50 caracteres.");
        }
        if(serviceCommon.createCode(newProducto) != null){
            productoEntity.setCodigo(serviceCommon.createCode(newProducto));
        }

        productoEntity.setPrecio_minorista(newProducto.getPrecio_compra().multiply(PORCENTAJE_MINORISTA));
        productoEntity.setPrecio_mayorista(newProducto.getPrecio_compra().multiply(PORCENTAJE_MAYORISTA));
        productoEntity.setDescripcion("");
        productoEntity.setActivo(false);
        productoEntity.setColor("");
        productoEntity.setDimensiones("");
        productoEntity.setPeso("");
        productoEntity.setMaterial("");
        productoEntity.setPaisOrigen("");
        productoEntity.setImageUrl("");
        productoEntity.setCategoriaEntities(new ArrayList<>());


        productoJpaRepository.save(productoEntity);

        ProductoDtoCompras productDto = new ProductoDtoCompras(productoEntity.getCodigo(), true, "Operación exitosa, el producto se ha creado correctamente.", productoEntity.getNombre(), productoEntity.getPrecio_compra());
        return ResponseEntity.ok(productDto);
    }



    @Override
    public ResponseEntity<ProductoDTODelete> deleteProducto(String code) {
        // Oftener el ProductEntity por ID
        ProductoEntity productoEntity = productoJpaRepository.getByCodigo(code);

        if (productoEntity == null) {
            throw new EntityNotFoundException(String.format("El producto con codigo '"+ code + "' no existe."));
        }

        productoEntity.setActivo(false);
        productoJpaRepository.save(productoEntity);

        return ResponseEntity.ok(new ProductoDTODelete(true,"Se ha inhabilitado el producto con codigo: "+productoEntity.getCodigo()));
    }


    @Override
    public ResponseEntity<ProductoDTO> updateProductoInactivo(Producto producto, String code) {
        // Oftener el ProductEntity por ID
        ProductoEntity productoEntity = productoJpaRepository.getByCodigo(code);

        if (productoEntity == null) {
            throw new EntityNotFoundException(String.format("El producto con codigo '"+ code + "' no existe."));
        }

        // Actualize los campos del ProductEntity con los dates del Product
        productoEntity.setDescripcion(producto.getDescripcion().toUpperCase());
        productoEntity.setPrecio_compra(producto.getPrecio_compra());
        productoEntity.setImageUrl(producto.getImageURL());
        productoEntity.setDimensiones(producto.getDimensiones().toUpperCase());
        productoEntity.setPeso(producto.getPeso().toUpperCase());
        productoEntity.setMaterial(producto.getMaterial().toUpperCase());
        productoEntity.setColor(producto.getColor().toUpperCase());
        productoEntity.setPaisOrigen(producto.getPaisOrigen().toUpperCase());
        productoEntity.setActivo(producto.isActivo());
        productoEntity.setPrecio_minorista(producto.getPrecio_compra().multiply(PORCENTAJE_MINORISTA));
        productoEntity.setPrecio_mayorista(producto.getPrecio_compra().multiply(PORCENTAJE_MAYORISTA));

        List<CategoriaEntity> categorias = new ArrayList<>();
        List<CategoriaDto> categoriasDto = new ArrayList<>();


        if (marcaJpaRepository.getByCodigo(producto.getCodigo_marca().toUpperCase()) == null && !producto.getCodigo_marca().isEmpty()){
            throw new RuntimeException("El codigo de la marca no existe.");
        }
        productoEntity.setMarcaEntity(marcaJpaRepository.getByCodigo(producto.getCodigo_marca().toUpperCase()));

        //mapea la lista de categorias del producto a una lista de categoriasEntity
        for (String codigoCategoria : producto.getCodigo_categorias()) {
            if(categoriaJpaRepository.getByCodigo(codigoCategoria.toUpperCase()) == null && !codigoCategoria.isEmpty()){
                throw new RuntimeException("El codigo de la categoria no existe.");
            }
            if(codigoCategoria.isEmpty()){
                throw new RuntimeException("No puedes poner una categoria vacia.");
            }
            CategoriaEntity categoriaEntity = categoriaJpaRepository.getByCodigo(codigoCategoria.toUpperCase());
            categorias.add(categoriaEntity);
            categoriasDto.add(modelMapper.map(categoriaEntity, CategoriaDto.class));
        }
        productoEntity.setCategoriaEntities(categorias);


        productoJpaRepository.save(productoEntity);
        ProductoDTO productoDTO = modelMapper.map(productoEntity, ProductoDTO.class);
        productoDTO.setDescuentos(serviceCommon.getAllDescuentosByProductCode(productoEntity.getCodigo()));
        productoDTO.setOfertas(serviceCommon.getAllOfertasByProductCode(productoEntity.getCodigo()));
        productoDTO.setCategorias(categoriasDto);

        return ResponseEntity.ok(productoDTO);
    }


    @Override
    public ResponseEntity<List<ProductoDTO>> productosActivosParameters(Boolean activo) {
        List<ProductoEntity> lista = productoJpaRepository.findAll().stream()
                .filter(productoEntity -> productoEntity.getActivo() == activo).toList();

        List<ProductoDTO> listaMapeada = lista.stream()
                .map(productoEntity -> {

                    ProductoDTO productoDTO = modelMapper.map(productoEntity, ProductoDTO.class);
                    productoDTO.setCategorias(new ArrayList<>());
                    serviceCommon.mapearCategoriasToProducts(productoDTO, productoDTO.getCategorias(), productoEntity);
                    return productoDTO;
                })
                .collect(toList());

        for (ProductoDTO p :listaMapeada) {
            p.setDescuentos(serviceCommon.getAllDescuentosByProductCode(p.getCodigo()));
            p.setOfertas(serviceCommon.getAllOfertasByProductCode(p.getCodigo()));
        }

        return ResponseEntity.ok(listaMapeada);
    }

    @Override
    public ResponseEntity<List<ProductoDTO>> getProdParams(String codigo, String nombre, String color, String material, BigDecimal precio_desde,
                                                           BigDecimal precio_hasta, String codigo_marca, List<String> codigo_categoria){


        List<ProductoEntity> entityList = productoJpaRepository.findAll();


        List<ProductoEntity> filteredList = entityList.stream()
                .filter(producto -> (codigo == null || codigo.isEmpty() || producto.getCodigo().contains(codigo)))
                .filter(producto -> (nombre == null || nombre.isEmpty() || producto.getNombre().contains(nombre)))
                .filter(producto -> (color == null || color.isEmpty() || producto.getColor().contains(color)))
                .filter(producto -> (material == null || material.isEmpty() || producto.getMaterial().contains(material)))
                .filter(producto -> (precio_desde == null || producto.getPrecio_minorista().compareTo(precio_desde) >= 0))
                .filter(producto -> (precio_hasta == null || producto.getPrecio_minorista().compareTo(precio_hasta) <= 0))
                .filter(producto -> (codigo_marca == null || codigo_marca.isEmpty() || producto.getMarcaEntity().getCodigo().contains(codigo_marca)))
                .filter(producto -> codigo_categoria == null || codigo_categoria.isEmpty() || codigo_categoria.stream().allMatch(categoria -> producto.getCategoriaEntities().stream().anyMatch(c -> c.getCodigo().equals(categoria))))
                .toList();

        return getListResponseEntity(filteredList);
    }



    //TODO: METODOS PRIVADOS



    /*Recibe una lista de productos como entidad y mapea a una lista nueva de tipo ProductoDto
    usando el metodo mapearCategoriasToProducts
    mapea las categorias */
    private ResponseEntity<List<ProductoDTO>> getListResponseEntity(List<ProductoEntity> lista) {
        List<ProductoDTO> listaMapeada = new ArrayList<>();
        for (ProductoEntity productoEntity : lista){
            ProductoDTO prodMapped = new ProductoDTO();
            prodMapped.setDescuentos(serviceCommon.getAllDescuentosByProductCode(productoEntity.getCodigo()));
            prodMapped.setOfertas(serviceCommon.getAllOfertasByProductCode(productoEntity.getCodigo()));
            List<CategoriaDto> catMapped = new ArrayList<>();
            serviceCommon.mapearCategoriasToProducts(prodMapped, catMapped, productoEntity);
            listaMapeada.add(prodMapped);
        }
        return ResponseEntity.ok(listaMapeada);
    }







}

