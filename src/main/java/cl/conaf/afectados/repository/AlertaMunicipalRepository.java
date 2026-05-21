package cl.conaf.afectados.repository;

import cl.conaf.afectados.entity.AlertaMunicipal;
import cl.conaf.afectados.enums.NivelUrgencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaMunicipalRepository extends JpaRepository<AlertaMunicipal, Long> {

    List<AlertaMunicipal> findByComuna(String comuna);
    List<AlertaMunicipal> findByNivelUrgencia(NivelUrgencia nivelUrgencia);
    List<AlertaMunicipal> findByEstado(String estado);
}