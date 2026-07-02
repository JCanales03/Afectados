package cl.conaf.afectados.controller;

import cl.conaf.afectados.dto.AlertaMunicipalDTO;
import cl.conaf.afectados.entity.AlertaMunicipal;
import cl.conaf.afectados.service.AlertaMunicipalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alertas")
@RequiredArgsConstructor
@Tag(name = "Alertas Municipales",
     description = "Gestión de alertas digitales enviadas por municipios afectados")
public class AlertaMunicipalController {

    private final AlertaMunicipalService alertaService;

    @Operation(
        summary = "Crear una alerta municipal",
        description = "La alcaldesa registra una alerta con comuna, sector, " +
                      "nivel de urgencia y familias evacuadas"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Alerta creada exitosamente",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AlertaMunicipal.class),
                examples = @ExampleObject(
                    name = "Alerta urgente",
                    value = """
                        {
                          "id": 1,
                          "comuna": "Constitución",
                          "sector": "Sector Cordillerano Norte",
                          "nivelUrgencia": "URGENTE",
                          "estado": "ACTIVA",
                          "fechaAlerta": "2026-05-28T10:30:00"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                        {
                          "status": 400,
                          "error": "Bad Request",
                          "message": "La comuna es obligatoria"
                        }
                        """
                )
            )
        )
    })
    @PostMapping
    public ResponseEntity<AlertaMunicipal> crear(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos de la alerta municipal",
            required = true,
            content = @Content(
                examples = @ExampleObject(
                    name = "Ejemplo alerta URGENTE",
                    value = """
                        {
                          "comuna": "Constitución",
                          "sector": "Sector Cordillerano Norte",
                          "nivelUrgencia": "URGENTE",
                          "descripcion": "Incendio avanza hacia sector residencial",
                          "infraestructuraRiesgo": "30 viviendas",
                          "cantidadFamilias": 30,
                          "cantidadPersonas": 87,
                          "puntoReunion": "Plaza de Armas Constitución",
                          "observaciones": "Familias requieren alojamiento"
                        }
                        """
                )
            )
        )
        @Valid @RequestBody AlertaMunicipalDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alertaService.crearAlerta(dto));
    }

    @Operation(
        summary = "Obtener todas las alertas",
        description = "Retorna la lista completa de alertas registradas"
    )
    @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente")
    @GetMapping
    public ResponseEntity<List<AlertaMunicipal>> obtenerTodas() {
        return ResponseEntity.ok(alertaService.obtenerTodas());
    }

    @Operation(
        summary = "Obtener alerta por ID",
        description = "Busca una alerta específica por su ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Alerta encontrada"),
        @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AlertaMunicipal> obtenerPorId(@PathVariable Long id) {
        return alertaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
        summary = "Actualizar estado de alerta",
        description = "Valores permitidos: ACTIVA, EN_PROCESO, RESUELTA, CERRADA"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Estado actualizado"),
        @ApiResponse(responseCode = "404", description = "Alerta no encontrada")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<AlertaMunicipal> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return ResponseEntity.ok(alertaService.actualizarEstado(id, estado));
    }

    @Operation(
        summary = "Eliminar alerta",
        description = "Elimina una alerta del sistema por su ID"
    )
    @ApiResponse(responseCode = "204", description = "Alerta eliminada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alertaService.eliminarAlerta(id);
        return ResponseEntity.noContent().build();
    }
}