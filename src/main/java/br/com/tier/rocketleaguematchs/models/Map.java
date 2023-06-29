package br.com.tier.rocketleaguematchs.models;

import org.hibernate.validator.constraints.NotBlank;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "map")
public class Map {

    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_map")
    private Integer id;

    @Column(name = "name_map", unique = true)
    private String name;
    
    @NotBlank
    @ManyToOne
    private Country country;



}
