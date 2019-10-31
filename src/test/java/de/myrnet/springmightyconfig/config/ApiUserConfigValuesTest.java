package de.myrnet.springmightyconfig.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static de.myrnet.springmightyconfig.config.DefaultConfig.Type.*;
import static de.myrnet.springmightyconfig.config.DefaultConfig.extractValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApiUserConfigValuesTest {

    @Autowired
    private DefaultConfig defaultConfig;

    @Test
    void getValue_allTypes() {
        ConfigValue<String> configValue = defaultConfig.getOrder().getDefaultShippingCompany();
        Assertions.assertAll(
                // Positive tests
                () -> assertEquals("Blue-Corp.", extractValue(BIKES, configValue),   "bikes type"),
                () -> assertEquals("YEL",        extractValue(PARTS, configValue),   "parts type"),
                () -> assertEquals("Packy",      extractValue(CLOTHES, configValue), "clothes type"),
                // Negative tests
                () -> assertThrows(IllegalArgumentException.class, () -> extractValue(unsupported, configValue), "error")
        );
    }

}
