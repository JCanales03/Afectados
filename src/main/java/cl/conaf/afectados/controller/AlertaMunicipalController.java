package cl.conaf.afectados.controller;

import cl.conaf.afectados.dto.AlertaMunicipalDTO;
import cl.conaf.afectados.entity.AlertaMunicipal;
import cl.conaf.afectados.service.AlertaMunicipalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alertas")
@RequiredArgsConstructor
public class AlertaMunicipalController {

    private final AlertaMunicipalService alertaService;

    @PostMapping
    public ResponseEntity<AlertaMunicipal> crear(@Valid @RequestBody AlertaMunicipalDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alertaService.crearAlerta(dto));
    }

    @GetMapping
    public ResponseEntity<List<AlertaMunicipal>> obtenerTodas() {
        return ResponseEntity.ok(alertaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaMunicipal> obtenerPorId(@PathVariable Long id) {
        return alertaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<AlertaMunicipal> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        return ResponseEntity.ok(alertaService.actualizarEstado(id, estado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alertaService.eliminarAlerta(id);
        return ResponseEntity.noContent().build();
    }
}