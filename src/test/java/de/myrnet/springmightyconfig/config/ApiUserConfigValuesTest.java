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
        ConfigValue<String> webPageConfUrlPath = defaultConfig.getWebPageConf().getUrlPath();
        ConfigValue<String> orderConfShiping = defaultConfig.getOrderConf().getDefaultShippingCompany();
        Assertions.assertAll(
                // Positive tests
                () -> assertEquals("/parts",    extractValue(PARTS,   webPageConfUrlPath), "URL parts"),
                () -> assertEquals("/clothes",  extractValue(CLOTHES, webPageConfUrlPath), "URL clothes"),
                () -> assertEquals("Blue-Corp.", extractValue(BIKES,   orderConfShiping), "bikes type"),
                () -> assertEquals("YEL",        extractValue(PARTS,   orderConfShiping), "parts type"),
                () -> assertEquals("Packy",      extractValue(CLOTHES, orderConfShiping), "clothes type"),
                // Negative tests
                () -> assertThrows(IllegalArgumentException.class,
                        () -> extractValue(unsupported, orderConfShiping), "order conf error"),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> extractValue(unsupported, webPageConfUrlPath), "web page conf error")
        );
    }

}
