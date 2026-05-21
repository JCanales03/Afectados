package cl.conaf.afectados.entity;

import cl.conaf.afectados.enums.NivelUrgencia;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "alerta_municipal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlertaMunicipal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comuna", nullable = false)
    private String comuna;

    @Column(name = "sector", nullable = false)
    private String sector;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_urgencia", nullable = false)
    private NivelUrgencia nivelUrgencia;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "infraestructura_riesgo")
    private String infraestructuraRiesgo;

    @Column(name = "fecha_alerta")
    private LocalDateTime fechaAlerta;

    @Column(name = "estado")
    private String estado;

    @OneToMany(mappedBy = "alerta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegistroEvacuacion> evacuaciones;
}
