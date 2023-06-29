package br.com.tier.rocketleaguematchs.models;

import org.hibernate.validator.constraints.NotBlank;

import br.com.tier.rocketleaguematchs.models.dto.PlayerMatchDTO;
import br.com.tier.rocketleaguematchs.utils.DateUtils;
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
@Entity(name = "playermatch")
public class PlayerMatch {

    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_playermatch")
    private Integer id;

    @Column(name = "goals_playermatch")
    private Integer goals;

    @NotBlank
    @ManyToOne
    private Player player;

    @NotBlank
    @ManyToOne
    private Match match;

    public PlayerMatch(PlayerMatchDTO dto, Player player, Match match) {
        this(dto.getId(), dto.getGoals(), player, match);
    }

    public PlayerMatchDTO toDTO() {
        return new PlayerMatchDTO(id, player.getId(), player.getName(), match.getId(),
                DateUtils.zoneDateToBrDate(match.getDate()), goals);
    }

}
