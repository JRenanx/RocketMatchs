package br.com.tier.rocketleaguematchs;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.tier.rocketleaguematchs.service.SeasonService;
import br.com.tier.rocketleaguematchs.service.impl.SeasonServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {
    

    @Bean
    public SeasonService seasonService() {
        return new SeasonServiceImpl();
    }
    
}
