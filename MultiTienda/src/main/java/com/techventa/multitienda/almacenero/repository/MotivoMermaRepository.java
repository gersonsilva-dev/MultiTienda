package com.techventa.multitienda.almacenero.repository;

import com.techventa.multitienda.admin.model.MotivoMerma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotivoMermaRepository extends JpaRepository<MotivoMerma, Integer> {
    
    // Método existente (no tocar)
    List<MotivoMerma> findByActivoTrueOrderByNombreMotivoAsc();
    
    // ✅ Si necesitas buscar por nombre específico
    List<MotivoMerma> findByNombreMotivoContainingIgnoreCase(String nombre);
    
    // ✅ Si necesitas verificar si existe un motivo por nombre
    boolean existsByNombreMotivo(String nombre);
}