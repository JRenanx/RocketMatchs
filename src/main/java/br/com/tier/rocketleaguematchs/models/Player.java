package br.com.tier.rocketleaguematchs.models;

import org.hibernate.validator.constraints.NotBlank;

import br.com.tier.rocketleaguematchs.models.dto.PlayerDTO;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_player")
    private Integer id;

    @Column(name = "name_player", unique = true)
    private String name;

    @NotBlank
    @ManyToOne
    private Team team;

    @NotBlank
    @ManyToOne
    private Country country;

    public Player(PlayerDTO dto, Team team, Country country) {
        this(dto.getId(), dto.getName(), team, country);
    }

    public PlayerDTO toDTO() {
        return new PlayerDTO(id, name, country.getId(), country.getName(), team.getId(), team.getName());
    }

}
