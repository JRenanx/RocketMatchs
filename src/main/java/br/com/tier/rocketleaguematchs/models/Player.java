package br.com.tier.rocketleaguematchs.models;

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
@Entity(name = "player")
public class Player {

    @Setter
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    @Id
    @Column(name = "id_player")
    private Integer id;
    

    @Column(name = "nome_player", unique = true)
    private String name;
    
 
    @ManyToOne
    private Team team;

    @ManyToOne
    private Country country;
    
    
}
