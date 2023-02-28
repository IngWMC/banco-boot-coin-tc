package com.nttdata.bc.services;

import java.time.LocalDate;

import org.bson.types.ObjectId;

import com.nttdata.bc.documents.TipoCambio;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface ITipoCambioService {
    Uni<TipoCambio> insert(TipoCambio obj);

    Uni<TipoCambio> update(TipoCambio obj);

    Multi<TipoCambio> listAll();

    Uni<TipoCambio> findById(ObjectId id);

    Uni<TipoCambio> findByDate(LocalDate fecha);

    Uni<Void> deleteById(ObjectId id);
}
