package br.com.tier.rocketleaguematchs.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of ="id")
@Entity(name = "jogador_partida")
public class MatchPlayer {

    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_JogadorPartida")
    private Integer id;
    
    @NotNull
    @NotBlank
    @Column(name = "gols_JogadorPartida")
    private Integer goal;
    
    @ManyToOne
    private Player player;
    
    @ManyToOne
    private Match match;
    
    
}