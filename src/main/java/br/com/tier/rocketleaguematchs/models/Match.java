package br.com.tier.rocketleaguematchs.models;

import java.time.ZonedDateTime;

import org.hibernate.validator.constraints.NotBlank;

import br.com.tier.rocketleaguematchs.models.dto.MatchDTO;
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
@Entity(name = "match")
public class Match {

    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_match")
    private Integer id;

    @Column(name = "date_match")
    private ZonedDateTime date;

    @NotBlank
    @ManyToOne
    private Map map;

    @NotBlank
    @ManyToOne
    private Season season;

    public Match(MatchDTO raceDTO, Map map, Season season) {
        this(raceDTO.getId(), DateUtils.dateBrToZoneDate(raceDTO.getDate()), map, season);
    }

    public MatchDTO toDTO() {
        return new MatchDTO(id, DateUtils.zoneDateToBrDate(date), map.getId(), map.getName(), season.getId(),
                season.getDescription());
    }

}
