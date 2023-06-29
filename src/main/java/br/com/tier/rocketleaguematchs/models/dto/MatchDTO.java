package br.com.tier.rocketleaguematchs.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchDTO {

    private Integer id;

    private String date;

    private Integer mapId;

    private String mapName;

    private Integer seasonId;

    private String seasonName;

}
