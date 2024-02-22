package ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.controllers;

import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTO;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDTODelete;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.dtos.ProductoDtoCompras;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.Producto;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.models.ProductoPreCarga;
import ar.edu.utn.frc.tup.lc.iv.modules.catalog.submodules.product.services.iServiceProducto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/products")
public class ProductoController {

    @Autowired
    iServiceProducto iServiceProducto;


    @GetMapping("")
    public ResponseEntity<List<ProductoDTO>> getAllProductos() {
        return iServiceProducto.getAllProductos();
    }

    @GetMapping("/{code}")
    public ResponseEntity<ProductoDTO> getProductoById(@PathVariable String code) {
        code = code.toUpperCase();
        return iServiceProducto.getProductoByCode(code);
    }

    @PostMapping("")
    public ResponseEntity<ProductoDtoCompras> createProduct(@RequestBody @Valid ProductoPreCarga newProducto) {
        return iServiceProducto.createProducto(newProducto);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<ProductoDTODelete> bajaDeProducto(@PathVariable String code) {
        code = code.toUpperCase();
        return iServiceProducto.deleteProducto(code);
    }

    @PutMapping("/{code}")
    public ResponseEntity<ProductoDTO> modificarProducto(@PathVariable String code, @RequestBody Producto producto) {
        code = code.toUpperCase();
        return iServiceProducto.updateProductoInactivo(producto,code);
    }



    @GetMapping("/actives-inactive")
    public ResponseEntity<List<ProductoDTO>> getProductoActivosParameters(@RequestParam(name = "activo", required = false) Boolean activo) {
        return iServiceProducto.productosActivosParameters(activo);
    }


    @GetMapping("/articulos")
    public ResponseEntity<List<ProductoDTO>> getProdParams(@RequestParam(name = "codigo", required = false) String codigo,
                                                           @RequestParam(name = "nombre", required = false) String nombre,
                                                           @RequestParam(name = "color", required = false) String color,
                                                           @RequestParam(name = "material", required = false) String material,
                                                           @RequestParam(name = "precio_desde", required = false) BigDecimal precio_desde,
                                                           @RequestParam(name = "precio_hasta", required = false) BigDecimal precio_hasta,
                                                           @RequestParam(name = "codigo_marca", required = false) String codigo_marca,
                                                           @RequestParam(name = "codigo_categoria", required = false) List<String> codigo_categoria) {
        if(codigo != null){
            codigo = codigo.toUpperCase();
        }
        if(nombre != null){
            nombre = nombre.toUpperCase();
        }
        if(color != null){
            color = color.toUpperCase();
        }
        if(material != null){
            material = material.toUpperCase();
        }
        if(codigo_marca != null){
            codigo_marca = codigo_marca.toUpperCase();
        }
        if(codigo_categoria != null){
            for (String c:codigo_categoria) {
                if(c != null){
                    c = c.toUpperCase();
                }
            }
        }
        return iServiceProducto.getProdParams(codigo,nombre,color,material,precio_desde,precio_hasta,codigo_marca,codigo_categoria);
    }






}
