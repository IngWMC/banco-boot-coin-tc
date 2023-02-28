package com.nttdata.bc.repositories;

import com.nttdata.bc.documents.TipoCambio;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TipoCambioRepository implements ReactivePanacheMongoRepository<TipoCambio> {

}
