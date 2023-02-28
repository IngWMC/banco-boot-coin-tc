package com.nttdata.bc.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.jboss.logging.Logger;

import com.nttdata.bc.documents.TipoCambio;
import com.nttdata.bc.exceptions.BadRequestException;
import com.nttdata.bc.exceptions.NotFoundException;
import com.nttdata.bc.repositories.TipoCambioRepository;
import com.nttdata.bc.services.ITipoCambioService;

import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TipoCambioService implements ITipoCambioService {
    @Inject
    Logger LOGGER;

    @Inject
    TipoCambioRepository repository;

    @Override
    public Uni<TipoCambio> insert(TipoCambio obj) {
        LOGGER.info("ENTRO");
        return this.validarFechaTipoCambio(obj.getFechaTipoCambio())
                .flatMap((v) -> {
                    obj.setEsActivo(true);
                    obj.setFechaCreacion(LocalDateTime.now());
                    return this.repository.persist(obj);
                });

    }

    @Override
    public Uni<TipoCambio> update(TipoCambio obj) {
        return this.repository.findById(obj.getId())
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("El tipo de cambio con el id: " + obj.getId() + ", no existe."))
                .flatMap((v) -> {
                    obj.setFechaModificacion(LocalDateTime.now());
                    return this.repository.update(obj);
                });
    }

    @Override
    public Multi<TipoCambio> listAll() {
        return this.repository.listAll(Sort.descending("fechaTipoCambio"))
                .onItem()
                .<TipoCambio>disjoint()
                .map(tipoCambio -> tipoCambio);
    }

    @Override
    public Uni<TipoCambio> findById(ObjectId id) {
        return this.repository.findById(id)
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("El tipo de cambio con el id: " + id + ", no existe."));
    }

    @Override
    public Uni<TipoCambio> findByDate(LocalDate fecha) {
        return this.repository.list("fechaTipoCambio", fecha)
                .onItem()
                .<TipoCambio>disjoint()
                .toUni();
    }

    @Override
    public Uni<Void> deleteById(ObjectId id) {
        return this.repository.findById(id)
                .onItem()
                .ifNull()
                .failWith(() -> new NotFoundException("El tipo de cambio con el id: " + id + ", no existe."))
                .flatMap((tipoCambio) -> {
                    tipoCambio.setEsActivo(false);
                    tipoCambio.setFechaModificacion(LocalDateTime.now());
                    return this.repository.update(tipoCambio);
                })
                .replaceWithVoid();

    }

    private Uni<Void> validarFechaTipoCambio(LocalDate fechaTipoCambio) {
        return this.repository.list("fechaTipoCambio", fechaTipoCambio)
                .onItem()
                .<TipoCambio>disjoint()
                .toUni()
                .onItem()
                .ifNotNull()
                .failWith(() -> new BadRequestException(
                        "Ya existe un tipo de cambio en la fecha: " + fechaTipoCambio.toString() + "."))
                .replaceWithVoid();
    }

}
