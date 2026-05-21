package cl.conaf.afectados.service;

import cl.conaf.afectados.dto.AlertaMunicipalDTO;
import cl.conaf.afectados.entity.AlertaMunicipal;
import cl.conaf.afectados.entity.RegistroEvacuacion;
import cl.conaf.afectados.repository.AlertaMunicipalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertaMunicipalService {

    private final AlertaMunicipalRepository alertaRepository;

    public AlertaMunicipal crearAlerta(AlertaMunicipalDTO dto) {
        AlertaMunicipal alerta = new AlertaMunicipal();
        alerta.setComuna(dto.getComuna());
        alerta.setSector(dto.getSector());
        alerta.setNivelUrgencia(dto.getNivelUrgencia());
        alerta.setDescripcion(dto.getDescripcion());
        alerta.setInfraestructuraRiesgo(dto.getInfraestructuraRiesgo());
        alerta.setFechaAlerta(LocalDateTime.now());
        alerta.setEstado("ACTIVA");

        RegistroEvacuacion evacuacion = new RegistroEvacuacion();
        evacuacion.setCantidadFamilias(dto.getCantidadFamilias());
        evacuacion.setCantidadPersonas(dto.getCantidadPersonas());
        evacuacion.setPuntoReunion(dto.getPuntoReunion());
        evacuacion.setObservaciones(dto.getObservaciones());
        evacuacion.setAlerta(alerta);

        alerta.setEvacuaciones(List.of(evacuacion));

        return alertaRepository.save(alerta);
    }

    public List<AlertaMunicipal> obtenerTodas() {
        return alertaRepository.findAll();
    }

    public Optional<AlertaMunicipal> obtenerPorId(Long id) {
        return alertaRepository.findById(id);
    }

    public AlertaMunicipal actualizarEstado(Long id, String nuevoEstado) {
        AlertaMunicipal alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada"));
        alerta.setEstado(nuevoEstado);
        return alertaRepository.save(alerta);
    }

    public void eliminarAlerta(Long id) {
        alertaRepository.deleteById(id);
    }
}