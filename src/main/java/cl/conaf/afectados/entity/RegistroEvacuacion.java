package cl.conaf.afectados.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "registro_evacuacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroEvacuacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alerta_id", nullable = false)
    private AlertaMunicipal alerta;

    @Column(name = "cantidad_personas")
    private Integer cantidadPersonas;

    @Column(name = "cantidad_familias")
    private Integer cantidadFamilias;

    @Column(name = "punto_reunion")
    private String puntoReunion;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}