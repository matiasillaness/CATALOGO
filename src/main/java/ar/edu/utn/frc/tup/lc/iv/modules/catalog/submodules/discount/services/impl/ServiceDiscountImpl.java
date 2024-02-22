package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.services.impl;

import ar.edu.utn.frc.tup.lc.iv.common.services.ServiceCommon;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos.*;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.entities.DescuentoEntity;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.repositories.DiscountJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.services.ServiceDiscount;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.repositories.ProductoJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

// TODO: ver si hace falta verificar si necesita estar activo el producto al que se le selecciona

@Service
public class ServiceDiscountImpl implements ServiceDiscount {

    @Autowired
    DiscountJpaRepository discountJpaRepository;
    @Autowired
    ProductoJpaRepository productoJpaRepository;
    @Autowired
    ServiceCommon serviceCommon;
    @Autowired
    ModelMapper modelMapper;
    @Override
    public ResponseEntity<List<DiscountDTOProducto>> getAllDescuentos() {
        List<DescuentoEntity> lista = discountJpaRepository.findAll();

        List<DiscountDTOProducto> listaDto = new ArrayList<>();
        for (DescuentoEntity e : lista) {
            DiscountDTOProducto descuento = modelMapper.map(e, DiscountDTOProducto.class);
            listaDto.add(descuento);
        }
        return ResponseEntity.ok(listaDto);
    }

    @Override
    public ResponseEntity<DiscountDTOProducto> getDescuentoByCodigo(String codigo) {
        DescuentoEntity descuentoEntity = discountJpaRepository.getByCodigo(codigo);

        if (descuentoEntity == null) {
            throw new RuntimeException("No se encontró un descuento con el código especificado.");
        }
        DiscountDTOProducto desc = modelMapper.map(descuentoEntity, DiscountDTOProducto.class);

        return ResponseEntity.ok(desc);
    }

    @Override
    public ResponseEntity<DiscountDTOProducto> getDescuentoByNombre(String nombre) {
        DescuentoEntity descuentoEntity = discountJpaRepository.getByNombre(nombre);

        if (descuentoEntity == null) {
            throw new RuntimeException("No se encontró un descuento con el nombre especificado.");
        }

        DiscountDTOProducto desc = modelMapper.map(descuentoEntity, DiscountDTOProducto.class);

        return ResponseEntity.ok(desc);
    }

    @Override
    public ResponseEntity<List<DiscountDTO>> getAllDescuentosByProductCode(String code) {
        List<DescuentoEntity> lista = discountJpaRepository.getAllByProducto_Codigo(code);

        List<DiscountDTO> listaDto = new ArrayList<>();

        for (DescuentoEntity e : lista) {
            DiscountDTO descuento = modelMapper.map(e, DiscountDTO.class);
            listaDto.add(descuento);
        }
        return ResponseEntity.ok(listaDto);
    }

    @Override
    public ResponseEntity<DiscountDTO> postDescuento(DiscountDTOPost newDescuento) {
        if (discountJpaRepository.getByNombre(newDescuento.getNombre()) != null) {
            throw new RuntimeException("Ya existe un descuento con ese nombre.");
        }
        if (newDescuento.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre del descuento no puede estar vacío.");
        }
        if (newDescuento.getNombre().length() > 50) {
            throw new RuntimeException("El nombre del descuento no puede tener más de 50 caracteres.");
        }
        if (productoJpaRepository.getByCodigo(newDescuento.getCodigo_producto()) == null) {
            throw new RuntimeException("No se encontró un producto con el código especificado.");
        }
        else {
            DescuentoEntity descuentoEntity = modelMapper.map(newDescuento, DescuentoEntity.class);
            if(serviceCommon.createCode(newDescuento) != null){
                descuentoEntity.setCodigo(serviceCommon.createCode(newDescuento));
            }else{
                throw new RuntimeException("Ocurrio un problema al crear el codigo.");
            }
            descuentoEntity.setProducto(productoJpaRepository.getByCodigo(newDescuento.getCodigo_producto()));
            BigDecimal precioMin = productoJpaRepository.getByCodigo(newDescuento.getCodigo_producto()).getPrecio_compra().multiply(new BigDecimal("1.05"));

            descuentoEntity.setPrecio_min(precioMin);
            discountJpaRepository.save(descuentoEntity);
            DiscountDTO desc = modelMapper.map(descuentoEntity, DiscountDTO.class);
            desc.setCodigo_producto(newDescuento.getCodigo_producto());

            return ResponseEntity.ok(desc);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<DiscountDTODelete> deleteDescuento(String code) {
        DescuentoEntity descuentoEntity = discountJpaRepository.getByCodigo(code);

        if (descuentoEntity == null) {
            throw new EntityNotFoundException(String.format("El descuento con codigo '"+ code + "' no existe."));
        }

        descuentoEntity.setActivo(false);
        discountJpaRepository.save(descuentoEntity);

        return ResponseEntity.ok(new DiscountDTODelete(true,"Se ha inhabilitado el descuento con codigo: "+descuentoEntity.getCodigo()));
    }

    @Override
    public ResponseEntity<DiscountDTOPut> putDescuento(DiscountDTOPut newDescuento, String code) {
        DescuentoEntity descuentoEntity = discountJpaRepository.getByCodigo(code);

        if (descuentoEntity == null) {
            throw new RuntimeException("No se encontró un descuento con el código especificado.");
        }

        String nuevoNombre = newDescuento.getNombre();

        DescuentoEntity descuentoByNuevoNombre = discountJpaRepository.getByNombre(nuevoNombre);

        if (descuentoByNuevoNombre != null && !descuentoByNuevoNombre.getId().equals(descuentoEntity.getId())) {
            throw new RuntimeException("Ya existe un descuento con ese nombre.");
        }

        if (nuevoNombre.isEmpty()) {
            throw new RuntimeException("El nombre del descuento no puede estar vacío.");
        }
        if (nuevoNombre.length() > 50) {
            throw new RuntimeException("El nombre del descuento no puede tener más de 50 caracteres.");
        }
        if (productoJpaRepository.getByCodigo(newDescuento.getCodigo_producto()) == null) {
            throw new RuntimeException("No se encontró un producto con el código especificado.");
        }

        BigDecimal precioMin = productoJpaRepository.getByCodigo(newDescuento.getCodigo_producto()).getPrecio_compra().multiply(new BigDecimal("1.05"));

        descuentoEntity.setNombre(nuevoNombre);
        descuentoEntity.setDescripcion(newDescuento.getDescripcion());
        descuentoEntity.setPrecio_min(precioMin);
        descuentoEntity.setProducto(productoJpaRepository.getByCodigo(newDescuento.getCodigo_producto()));
        descuentoEntity.setActivo(newDescuento.isActivo());

        DescuentoEntity descuentoSaved = discountJpaRepository.save(descuentoEntity);
        DiscountDTOPut discountDTOPut = modelMapper.map(descuentoSaved, DiscountDTOPut.class);
        discountDTOPut.setCodigo_producto(descuentoSaved.getProducto().getCodigo());
        return ResponseEntity.ok(discountDTOPut);
    }
}
