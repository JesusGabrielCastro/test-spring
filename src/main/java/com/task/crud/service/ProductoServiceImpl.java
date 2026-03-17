package com.task.crud.service;

import com.task.crud.dto.PageResponse;
import com.task.crud.dto.ProductoRequest;
import com.task.crud.dto.ProductoResponse;
import com.task.crud.entity.Producto;
import com.task.crud.exception.ResourceNotFoundException;
import com.task.crud.repository.ProductoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repository;

    public ProductoServiceImpl(ProductoRepository repository) {
        this.repository = repository;
    }

    @Override
    public PageResponse<ProductoResponse> obtenerTodos(int page, int size,
                                                        String sortBy, String direction) {
        Pageable pageable = crearPageable(page, size, sortBy, direction);
        Page<Producto> resultado = repository.findAll(pageable);
        return toPageResponse(resultado);
    }

    @Override
    public PageResponse<ProductoResponse> filtrar(String nombre, Double precioMin,
                                                   Double precioMax, int page, int size,
                                                   String sortBy, String direction) {
        Pageable pageable = crearPageable(page, size, sortBy, direction);
        Page<Producto> resultado = repository.filtrar(nombre, precioMin, precioMax, pageable);
        return toPageResponse(resultado);
    }

    @Override
    public ProductoResponse obtenerPorId(Long id) {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return toResponse(producto);
    }

    @Override
    public ProductoResponse crear(ProductoRequest request) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setDescripcion(request.getDescripcion());

        Producto guardado = repository.save(producto);
        return toResponse(guardado);
    }

    @Override
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto producto = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));

        producto.setNombre(request.getNombre());
        producto.setPrecio(request.getPrecio());
        producto.setDescripcion(request.getDescripcion());

        Producto actualizado = repository.save(producto);
        return toResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    // --- Métodos helper privados ---

    private Pageable crearPageable(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return PageRequest.of(page - 1, size, sort);  // page-1 porque internamente es 0-indexed
    }

    private PageResponse<ProductoResponse> toPageResponse(Page<Producto> page) {
        return new PageResponse<>(
                page.getContent().stream().map(this::toResponse).toList(),
                page.getNumber() + 1,  // +1 para devolver 1-indexed al cliente
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    private ProductoResponse toResponse(Producto p) {
        return new ProductoResponse(
                p.getId(),
                p.getNombre(),
                p.getPrecio(),
                p.getDescripcion(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}