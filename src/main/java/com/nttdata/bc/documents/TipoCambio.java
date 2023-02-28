package com.nttdata.bc.documents;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.quarkus.mongodb.panache.common.MongoEntity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@MongoEntity(collection = "tipoCambio")
public class TipoCambio {
    private ObjectId id;

    @DecimalMin(value = "0.001", message = "El campo compra debe tener un valor mínimo de '0.001'.")
    @Digits(integer = 10, fraction = 3, message = "El campo compra tiene un formato no válido (#####.000).")
    @NotNull(message = "El campo compra es requerido.")
    @BsonProperty("compra")
    private BigDecimal compra;

    @DecimalMin(value = "0.001", message = "El campo venta debe tener un valor mínimo de '0.001'.")
    @Digits(integer = 10, fraction = 3, message = "El campo venta tiene un formato no válido (#####.000).")
    @NotNull(message = "El campo venta es requerido.")
    @BsonProperty("venta")
    private BigDecimal venta;

    // @Pattern(regexp =
    // "\\d{4}[\\-](0?[1-9]|1[012])[\\-](0?[1-9]|[12][0-9]|3[01])", message = "El
    // campo documentIdentityType debe tener el siguiente formato YYYY-MM-DD.")
    @NotNull(message = "El campo fechaTipoCambio es requerido.")
    @BsonProperty("fechaTipoCambio")
    private LocalDate fechaTipoCambio;

    @BsonProperty("esActivo")
    private Boolean esActivo;

    @BsonProperty("fechaCreacion")
    private LocalDateTime fechaCreacion;

    @BsonProperty("fechaModificacion")
    private LocalDateTime fechaModificacion;
}
