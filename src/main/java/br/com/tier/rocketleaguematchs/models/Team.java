package br.com.tier.rocketleaguematchs.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity(name = "equipe")
public class Team {

    @Setter
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Id
    @Column(name = "id_equipe")
    private Integer id;
    
    @Column(name = "nome_equipe", unique = true)
    private String name;
}
