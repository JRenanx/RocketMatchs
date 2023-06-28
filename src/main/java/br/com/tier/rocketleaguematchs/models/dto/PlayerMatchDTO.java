package br.com.tier.rocketleaguematchs.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerMatchDTO {

    private Integer id;
    
    private Integer playerId;
    
    private String playerName;
    
    private Integer matchId;
    
    private String matchDate;
    
    private Integer goals;
}
