package com.task.crud.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

@Schema(description = "Datos del producto en la respuesta")
public class ProductoResponse {

    @Schema(description = "ID único del producto", example = "1")
    private Long id;

    @Schema(description = "Nombre del producto", example = "Laptop Lenovo ThinkPad")
    private String nombre;

    @Schema(description = "Precio en COP", example = "2500000")
    private Double precio;

    @Schema(description = "Descripción del producto", example = "Laptop para desarrollo")
    private String descripcion;

    @Schema(description = "Fecha de creación")
    private LocalDateTime createdAt;

    @Schema(description = "Última actualización")
    private LocalDateTime updatedAt;

    public ProductoResponse(Long id, String nombre, Double precio,
                            String descripcion, LocalDateTime createdAt,
                            LocalDateTime updatedAt) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Double getPrecio() { return precio; }
    public String getDescripcion() { return descripcion; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}