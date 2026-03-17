package com.task.crud.controller;

import com.task.crud.dto.PageResponse;
import com.task.crud.dto.ProductoRequest;
import com.task.crud.dto.ProductoResponse;
import com.task.crud.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Operaciones CRUD para gestión de productos")
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar productos", description = "Retorna todos los productos con paginación y ordenamiento")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    @GetMapping
    public ResponseEntity<PageResponse<ProductoResponse>> listarTodos(
            @Parameter(description = "Número de página (desde 1)", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Cantidad de elementos por página", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenar (id, nombre, precio)", example = "id")
            @RequestParam(defaultValue = "id") String sort,
            @Parameter(description = "Dirección del orden: asc o desc", example = "asc")
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(service.obtenerTodos(page, size, sort, direction));
    }

    @Operation(summary = "Filtrar productos", description = "Busca productos por nombre y/o rango de precio. Todos los filtros son opcionales")
    @ApiResponse(responseCode = "200", description = "Resultados del filtro")
    @GetMapping("/filtrar")
    public ResponseEntity<PageResponse<ProductoResponse>> filtrar(
            @Parameter(description = "Buscar por nombre (parcial, case insensitive)", example = "laptop")
            @RequestParam(required = false) String nombre,
            @Parameter(description = "Precio mínimo", example = "100000")
            @RequestParam(required = false) Double precioMin,
            @Parameter(description = "Precio máximo", example = "5000000")
            @RequestParam(required = false) Double precioMax,
            @Parameter(description = "Número de página", example = "1")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Elementos por página", example = "10")
            @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Campo para ordenar", example = "precio")
            @RequestParam(defaultValue = "id") String sort,
            @Parameter(description = "Dirección del orden", example = "asc")
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(
                service.filtrar(nombre, precioMin, precioMax, page, size, sort, direction)
        );
    }

    @Operation(summary = "Obtener producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerPorId(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @Operation(summary = "Crear producto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ProductoResponse> crear(
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @Operation(summary = "Actualizar producto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @Operation(summary = "Eliminar producto")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID del producto", example = "1")
            @PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}