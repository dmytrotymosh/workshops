package org.example.workshop.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cnc_machines")
public class CncMachine {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "workshop_id", nullable = false)
    private Workshop workshop;
    @Column(name = "maker")
    private String maker;
    @Column(name = "country_of_origin")
    private String countryOfOrigin;
    @Column(name = "model")
    private String model;
    @Column(name = "length", nullable = false)
    private double length;
    @Column(name = "width", nullable = false)
    private double width;
    @Column(name = "height", nullable = false)
    private double height;
    @Column(name = "weight", nullable = false)
    private double weight;
    @Column(name = "table_work_area")
    private Double tableWorkArea;
    @Column(name = "max_power_consumption", nullable = false)
    private int maxPowerConsumption;
    @Column(name = "voltage", nullable = false)
    private int voltage;
    @Column(name = "command_language")
    private String commandLanguage;
    @Column(name = "operating_system")
    private String operatingSystem;
    @CreationTimestamp
    private Instant created;
    @UpdateTimestamp
    private Instant updated;
}
