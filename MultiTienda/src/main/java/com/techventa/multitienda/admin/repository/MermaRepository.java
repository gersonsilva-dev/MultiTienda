package com.techventa.multitienda.admin.repository;

import com.techventa.multitienda.admin.model.Merma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MermaRepository extends JpaRepository<Merma, Integer> {

    List<Merma> findByActivoTrue();
    List<Merma> findByProducto_IdProducto(Integer idProducto);
    List<Merma> findByTienda_IdTienda(Integer idTienda);
    List<Merma> findByMotivoMerma_IdMotivoMerma(Integer idMotivo);
    List<Merma> findByAlmacenero_IdUsuario(Integer idAlmacenero);
    List<Merma> findByEstadoAutorizacion(String estadoAutorizacion);
    List<Merma> findByFechaMermaBetween(LocalDateTime inicio, LocalDateTime fin);
    List<Merma> findByCantidadGreaterThanEqual(Integer cantidad);
}