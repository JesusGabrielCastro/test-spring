package com.task.crud.repository;

import com.task.crud.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // Buscar por nombre (LIKE, case insensitive)
    // Equivale a: Producto.objects.filter(nombre__icontains=nombre)
    Page<Producto> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    // Filtrar por rango de precio
    // Equivale a: Producto.objects.filter(precio__gte=min, precio__lte=max)
    Page<Producto> findByPrecioBetween(Double precioMin, Double precioMax, Pageable pageable);

    // Combinado: nombre + rango de precio
    @Query("SELECT p FROM Producto p WHERE " +
           "(:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
           "(:precioMin IS NULL OR p.precio >= :precioMin) AND " +
           "(:precioMax IS NULL OR p.precio <= :precioMax)")
    Page<Producto> filtrar(
            @Param("nombre") String nombre,
            @Param("precioMin") Double precioMin,
            @Param("precioMax") Double precioMax,
            Pageable pageable
    );
}