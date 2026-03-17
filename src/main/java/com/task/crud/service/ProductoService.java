package com.task.crud.service;

import com.task.crud.dto.PageResponse;
import com.task.crud.dto.ProductoRequest;
import com.task.crud.dto.ProductoResponse;


public interface ProductoService {

    PageResponse<ProductoResponse> obtenerTodos(int page, int size, String sortBy, String direction);
    PageResponse<ProductoResponse> filtrar(String nombre, Double precioMin, Double precioMax,
                                            int page, int size, String sortBy, String direction);
    ProductoResponse obtenerPorId(Long id);
    ProductoResponse crear(ProductoRequest request);
    ProductoResponse actualizar(Long id, ProductoRequest request);
    void eliminar(Long id);
}