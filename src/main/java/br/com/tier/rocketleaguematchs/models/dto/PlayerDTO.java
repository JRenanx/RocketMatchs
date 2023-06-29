package br.com.tier.rocketleaguematchs.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDTO {

    private Integer id;

    private String name;

    private Integer countryId;

    private String countryName;

    private Integer teamId;

    private String teamName;

}
