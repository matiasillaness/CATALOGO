package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.services;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.*;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.Producto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.ProductoPreCarga;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface iServiceProducto {

    ResponseEntity<List<ProductoDTO>> getAllProductos();
    ResponseEntity<ProductoDTO> getProductoByCode(String code);
    ResponseEntity<ProductoDtoCompras> createProducto(ProductoPreCarga newMarca);
    ResponseEntity<ProductoDTODelete> deleteProducto(String code);
    ResponseEntity<ProductoDTO> updateProductoInactivo(Producto producto, String code);
    ResponseEntity<List<ProductoDTO>> productosActivosParameters(Boolean activo);
    ResponseEntity<List<ProductoDTO>> getProdParams(String codigo, String nombre, String color, String material, BigDecimal precio_desde,
                                                    BigDecimal precio_hasta, String codigo_marca, List<String> codigo_categoria);
}
