package org.example.workshop.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workshops")
public class Workshop {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(updatable = false, nullable = false)
    private UUID id;
    @Column(name = "code")
    private String code;
    @Column(name = "height", nullable = false)
    private double height;
    @Column(name = "width", nullable = false)
    private double width;
    @Column(name = "length", nullable = false)
    private double length;
    @Column(name = "floor_load_capacity", nullable = false)
    private double floorLoadCapacity;
    @Column(name = "voltage", nullable = false)
    private int voltage;
    @Column(name = "available_power", nullable = false)
    private int availablePower;
    @CreationTimestamp
    private Instant created;
    @UpdateTimestamp
    private Instant updated;
    @OneToMany(mappedBy = "workshop", cascade = CascadeType.ALL)
    private List<CncMachine> cncMachines;
}
