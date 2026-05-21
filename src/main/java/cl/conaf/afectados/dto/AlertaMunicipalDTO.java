package cl.conaf.afectados.dto;

import cl.conaf.afectados.enums.NivelUrgencia;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AlertaMunicipalDTO {

    @NotBlank(message = "La comuna es obligatoria")
    private String comuna;

    @NotBlank(message = "El sector es obligatorio")
    private String sector;

    @NotNull(message = "El nivel de urgencia es obligatorio")
    private NivelUrgencia nivelUrgencia;

    private String descripcion;

    private String infraestructuraRiesgo;

    @Min(value = 0, message = "La cantidad de familias no puede ser negativa")
    private Integer cantidadFamilias;

    @Min(value = 0, message = "La cantidad de personas no puede ser negativa")
    private Integer cantidadPersonas;

    private String puntoReunion;

    private String observaciones;
}