package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.services.impl;

import ar.edu.utn.frc.tup.lc.iv.common.services.ServiceCommon;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.dtos.*;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.entities.OfertaEntity;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.repositories.OfferJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.services.ServiceOferta;
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
public class ServiceOfertaImpl implements ServiceOferta {

    @Autowired
    OfferJpaRepository offerJpaRepository;

    @Autowired
    ProductoJpaRepository productoJpaRepository;

    @Autowired
    ServiceCommon serviceCommon;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ResponseEntity<List<OfferDTOProducto>> getAllOfertas() {
        List<OfertaEntity> lista = offerJpaRepository.findAll();

        List<OfferDTOProducto> listaDto = new ArrayList<>();

        for (OfertaEntity e : lista) {
            OfferDTOProducto oferta = modelMapper.map(e, OfferDTOProducto.class);
            listaDto.add(oferta);
        }
        return ResponseEntity.ok(listaDto);
    }

    @Override
    public ResponseEntity<OfferDTOProducto> getOfertaByCodigo(String codigo) {
        OfertaEntity ofertaEntity = offerJpaRepository.getByCodigo(codigo);

        if (ofertaEntity == null) {
            throw new RuntimeException("No se encontró una oferta con el código especificado.");
        }

        OfferDTOProducto oferta = modelMapper.map(ofertaEntity, OfferDTOProducto.class);
        return ResponseEntity.ok(oferta);
    }

    @Override
    public ResponseEntity<OfferDTOProducto> getOfertaByNombre(String nombre) {
        OfertaEntity ofertaEntity = offerJpaRepository.getByNombre(nombre);

        if (ofertaEntity == null) {
            throw new RuntimeException("No se encontró una oferta con el nombre especificado.");
        }

        OfferDTOProducto oferta = modelMapper.map(ofertaEntity, OfferDTOProducto.class);
        return ResponseEntity.ok(oferta);
    }

    @Override
    public ResponseEntity<List<OfferDTO>> getAllOfertasByProductCode(String code) {
        List<OfertaEntity> lista = offerJpaRepository.getAllByProducto_Codigo(code);

        List<OfferDTO> listaDto = new ArrayList<>();

        for (OfertaEntity e : lista) {
            OfferDTO oferta = modelMapper.map(e, OfferDTO.class);
            listaDto.add(oferta);
        }
        return ResponseEntity.ok(listaDto);
    }

    @Override
    public ResponseEntity<OfferDTO> postOferta(OfferDTOPost newOferta) {
        if (offerJpaRepository.getByNombre(newOferta.getNombre()) != null) {
            throw new RuntimeException("Ya existe una oferta con ese nombre.");
        }
        if (newOferta.getNombre().isEmpty()) {
            throw new RuntimeException("El nombre de la oferta no puede estar vacío.");
        }
        if (newOferta.getNombre().length() > 50) {
            throw new RuntimeException("El nombre de la oferta no puede tener más de 50 caracteres.");
        }
        if (productoJpaRepository.getByCodigo(newOferta.getCodigo_producto()) == null) {
            throw new RuntimeException("No se encontró un producto con el código especificado.");
        }
        else {
            OfertaEntity ofertaEntity = modelMapper.map(newOferta, OfertaEntity.class);
            if(serviceCommon.createCode(newOferta) != null){
                ofertaEntity.setCodigo(serviceCommon.createCode(newOferta));
            }else{
                throw new RuntimeException("Ocurrio un problema al crear el codigo.");
            }
            ofertaEntity.setProducto(productoJpaRepository.getByCodigo(newOferta.getCodigo_producto()));
            ofertaEntity.setPuntos(calculoPuntos(newOferta.getPrecio_oferta()));
            offerJpaRepository.save(ofertaEntity);
            OfferDTO offerDTO = modelMapper.map(ofertaEntity, OfferDTO.class);
            offerDTO.setCodigo_producto(newOferta.getCodigo_producto());
            return ResponseEntity.ok(offerDTO);
        }
    }

    @Transactional
    @Override
    public ResponseEntity<OfferDTODelete> deleteOferta(String code) {
        OfertaEntity ofertaEntity = offerJpaRepository.getByCodigo(code);

        if (ofertaEntity == null) {
            throw new EntityNotFoundException(String.format("La oferta con codigo '"+ code + "' no existe."));
        }

        ofertaEntity.setActivo(false);
        offerJpaRepository.save(ofertaEntity);

        return ResponseEntity.ok(new OfferDTODelete(true,"Se ha inhabilitado la oferta con codigo: "+ofertaEntity.getCodigo()));
    }

    @Override
    public ResponseEntity<OfferDTOPut> putOferta(OfferDTOPut newOferta, String code) {
        OfertaEntity ofertaEntity = offerJpaRepository.getByCodigo(code);

        if (ofertaEntity == null) {
            throw new RuntimeException("No se encontró una oferta con el código especificado.");
        }

        String nuevoNombre = newOferta.getNombre();

        OfertaEntity ofertaByNuevoNombre = offerJpaRepository.getByNombre(nuevoNombre);

        if (ofertaByNuevoNombre != null && !ofertaByNuevoNombre.getId().equals(ofertaEntity.getId())) {
            throw new RuntimeException("Ya existe una oferta con ese nombre.");
        }

        if (nuevoNombre.isEmpty()) {
            throw new RuntimeException("El nombre de la oferta no puede estar vacío.");
        }
        if (nuevoNombre.length() > 50) {
            throw new RuntimeException("El nombre de la oferta no puede tener más de 50 caracteres.");
        }
        if (productoJpaRepository.getByCodigo(newOferta.getCodigo_producto()) == null) {
            throw new RuntimeException("No se encontró un producto con el código especificado.");
        }

        ofertaEntity.setNombre(nuevoNombre);
        ofertaEntity.setDescripcion(newOferta.getDescripcion());
        ofertaEntity.setPrecio_oferta(newOferta.getPrecio_oferta());
        ofertaEntity.setPuntos(calculoPuntos(newOferta.getPrecio_oferta()));
        ofertaEntity.setProducto(productoJpaRepository.getByCodigo(newOferta.getCodigo_producto()));
        ofertaEntity.setActivo(newOferta.isActivo());

        OfertaEntity ofertaEntityNueva = offerJpaRepository.save(ofertaEntity);
        OfferDTOPut offerDTOPut = modelMapper.map(ofertaEntityNueva, OfferDTOPut.class);
        offerDTOPut.setCodigo_producto(ofertaEntityNueva.getProducto().getCodigo());
        return ResponseEntity.ok(offerDTOPut);
    }

    //******************
    // METODOS PRIVADOS
    //******************

    private BigDecimal calculoPuntos(BigDecimal precio_oferta){

        BigDecimal puntos = precio_oferta.divideToIntegralValue(new BigDecimal("100"));
        return puntos.multiply(new BigDecimal("1.1"));

    }
}
