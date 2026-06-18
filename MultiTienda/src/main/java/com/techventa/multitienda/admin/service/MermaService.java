package com.techventa.multitienda.admin.service;

import com.techventa.multitienda.admin.model.*;
import com.techventa.multitienda.admin.repository.*;
import com.techventa.multitienda.almacenero.model.InventarioTienda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MermaService {

    @Autowired
    private MermaRepository mermaRepository;

    @Autowired
    private InventarioTiendaRepository inventarioTiendaRepository;

    @Autowired
    private LoteRepository loteRepository;

    @Autowired
    private MovimientoKardexRepository movimientoKardexRepository;

    @Autowired
    private TipoMovimientoInventarioRepository tipoMovimientoRepository;

    public List<Merma> listarTodas() { return mermaRepository.findAll(); }
    public List<Merma> listarActivas() { return mermaRepository.findByActivoTrue(); }
    public List<Merma> listarPorProducto(Integer idProducto) { return mermaRepository.findByProducto_IdProducto(idProducto); }
    public List<Merma> listarPorTienda(Integer idTienda) { return mermaRepository.findByTienda_IdTienda(idTienda); }
    public List<Merma> listarPorMotivo(Integer idMotivo) { return mermaRepository.findByMotivoMerma_IdMotivoMerma(idMotivo); }
    public Optional<Merma> buscarPorId(Integer id) { return mermaRepository.findById(id); }

    @Transactional
    public Merma crear(Merma merma) {
        merma.setActivo(true);
        merma.setFechaMerma(LocalDateTime.now());

        // Actualizar stock
        if (merma.getLote() != null && merma.getLote().getIdLote() != null) {
            Optional<Lote> loteOpt = loteRepository.findById(merma.getLote().getIdLote());
            if (loteOpt.isPresent()) {
                Lote lote = loteOpt.get();
                lote.setStockLote(lote.getStockLote() - merma.getCantidad());
                loteRepository.save(lote);
            }
        }

        Optional<InventarioTienda> invOpt = inventarioTiendaRepository
                .findByProducto_IdProductoAndTienda_IdTienda(
                        merma.getProducto().getIdProducto(),
                        merma.getTienda().getIdTienda());

        if (invOpt.isPresent()) {
            InventarioTienda inventario = invOpt.get();
            int stockAntes = inventario.getStockActual();
            inventario.setStockActual(stockAntes - merma.getCantidad());
            inventarioTiendaRepository.save(inventario);

            // Registrar en Kardex
            registrarKardex(merma, stockAntes, inventario.getStockActual());
        }

        return mermaRepository.save(merma);
    }

    private void registrarKardex(Merma merma, int stockAntes, int stockDespues) {
        MovimientoKardex movimiento = new MovimientoKardex();
        movimiento.setProducto(merma.getProducto());
        movimiento.setTienda(merma.getTienda());
        movimiento.setLote(merma.getLote());
        movimiento.setCantidad(merma.getCantidad());
        movimiento.setStockAntes(stockAntes);
        movimiento.setStockDespues(stockDespues);
        movimiento.setIdReferencia(merma.getIdMerma());
        movimiento.setTablaReferencia("mermas");
        movimiento.setObservacion("Merma: " + merma.getMotivoMerma().getNombreMotivo());
        movimiento.setFechaMovimiento(LocalDateTime.now());
        movimiento.setUsuarioCreacion(merma.getAlmacenero());

        Optional<TipoMovimientoInventario> tipoMov = tipoMovimientoRepository.findByNombreMovimiento("MERMA");
        tipoMov.ifPresent(movimiento::setTipoMovimiento);

        movimientoKardexRepository.save(movimiento);
    }

    public Merma actualizar(Merma merma) { return mermaRepository.save(merma); }

    public void eliminarLogico(Integer id) {
        mermaRepository.findById(id).ifPresent(m -> {
            m.setActivo(false);
            mermaRepository.save(m);
        });
    }

    public void eliminarFisico(Integer id) { mermaRepository.deleteById(id); }
}