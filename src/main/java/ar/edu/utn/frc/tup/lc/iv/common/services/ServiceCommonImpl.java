package ar.edu.utn.frc.tup.lc.iv.common.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.dtos.create.CatalogCreateDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.repositories.CatalogJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.dtos.MarcaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.brand.repositories.MarcaJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.dtos.CategoriaDtoPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.entities.CategoriaEntity;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.category.repositories.CategoriaJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos.DiscountDTOPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.dtos.DiscountDTOSimple;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.entities.DescuentoEntity;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.discount.repositories.DiscountJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.dtos.OfferDTOPost;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.dtos.OfferDTOSimple;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.entities.OfertaEntity;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.offer.repositories.OfferJpaRepository;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.entities.ProductoEntity;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.ProductoPreCarga;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.repositories.ProductoJpaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceCommonImpl implements ServiceCommon {

    @Autowired
    ProductoJpaRepository productoJpaRepository;
    @Autowired
    MarcaJpaRepository marcaJpaRepository;
    @Autowired
    CategoriaJpaRepository categoriaJpaRepository;
    @Autowired
    OfferJpaRepository offerJpaRepository;
    @Autowired
    DiscountJpaRepository discountJpaRepository;
    @Autowired
    CatalogJpaRepository catalogJpaRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public String createCode(Object clase) {

        String code;

        if (clase instanceof ProductoPreCarga) {
            code = "P-" + ((ProductoPreCarga) clase).getNombre().substring(0, 3).toUpperCase() + "-";
            for (int i = 1; i < 999; i++) {
                String ceros = calculoDeCeros(i);
                if (!productoJpaRepository.existsByCodigo(code + ceros)) {
                    code += ceros;
                    break;
                }
            }
            return code;
        }
        if (clase instanceof MarcaDtoPost) {
            code = "M-" + ((MarcaDtoPost) clase).getNombre().substring(0, 3).toUpperCase() + "-";
            for (int i = 1; i < 999; i++) {
                String ceros = calculoDeCeros(i);
                if (!marcaJpaRepository.existsByCodigo(code + ceros)) {
                    code += ceros;
                    break;
                }
            }
            return code;
        }
        if (clase instanceof CategoriaDtoPost) {
            code = "C-" + ((CategoriaDtoPost) clase).getNombre().substring(0, 3).toUpperCase() + "-";
            for (int i = 1; i < 999; i++) {
                String ceros = calculoDeCeros(i);
                if (!categoriaJpaRepository.existsByCodigo(code + ceros)) {
                    code += ceros;
                    break;
                }
            }
            return code;
        }
        if (clase instanceof OfferDTOPost) {
            code = "O-" + ((OfferDTOPost) clase).getNombre().substring(0, 3).toUpperCase() + "-";
            for (int i = 1; i < 999; i++) {
                String ceros = calculoDeCeros(i);
                if (!offerJpaRepository.existsByCodigo(code + ceros)) {
                    code += ceros;
                    break;
                }
            }
            return code;
        }
        if (clase instanceof DiscountDTOPost) {
            code = "D-" + ((DiscountDTOPost) clase).getNombre().substring(0, 3).toUpperCase() + "-";
            for (int i = 1; i < 999; i++) {
                String ceros = calculoDeCeros(i);
                if (!discountJpaRepository.existsByCodigo(code + ceros)) {
                    code += ceros;
                    break;
                }
            }
            return code;
        }
        if (clase instanceof CatalogCreateDTO) {
            code = "CA-" + ((CatalogCreateDTO) clase).getNombre().substring(0, 3).toUpperCase() + "-";
            for (int i = 1; i < 999; i++) {
                String ceros = calculoDeCeros(i);
                if (!catalogJpaRepository.existsByCodigo(code + ceros)) {
                    code += ceros;
                    break;
                }
            }
            return code;
        }

        throw new RuntimeException("Ocurrio un problema al crear el codigo.");
    }

    @Override
    public void mapearCategoriasToProducts(ProductoDTO prodMapped, List<CategoriaDto> catMapped, ProductoEntity productoEntity) {
        for (CategoriaEntity cat : productoEntity.getCategoriaEntities()) {
            CategoriaDto categoriaMapped = new CategoriaDto();
            categoriaMapped.setCodigo(cat.getCodigo());
            categoriaMapped.setNombre(cat.getNombre());
            catMapped.add(categoriaMapped);
        }
        prodMapped.setCategorias(catMapped);
        modelMapper.map(productoEntity, prodMapped);
    }

    @Override
    public List<DiscountDTOSimple> getAllDescuentosByProductCode(String code) {
        List<DescuentoEntity> lista = discountJpaRepository.getAllByProducto_Codigo(code);

        List<DiscountDTOSimple> listaDto = new ArrayList<>();

        for (DescuentoEntity e : lista) {
            DiscountDTOSimple descuento = modelMapper.map(e, DiscountDTOSimple.class);
            listaDto.add(descuento);
        }
        return listaDto;
    }

    @Override
    public List<OfferDTOSimple> getAllOfertasByProductCode(String code) {
        List<OfertaEntity> lista = offerJpaRepository.getAllByProducto_Codigo(code);

        List<OfferDTOSimple> listaDto = new ArrayList<>();

        for (OfertaEntity e : lista) {
            OfferDTOSimple oferta = modelMapper.map(e, OfferDTOSimple.class);
            listaDto.add(oferta);
        }
        return listaDto;
    }

    // **********************
    // METODOS PRIVADOS
    // **********************

    private String calculoDeCeros(int i) {
        if (i < 10) {
            return "00" + i;
        }
        if (i < 100) {
            return "0" + i;
        } else {
            return "" + i;
        }
    }
}