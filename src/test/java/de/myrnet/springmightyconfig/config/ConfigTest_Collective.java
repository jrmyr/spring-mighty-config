package de.myrnet.springmightyconfig.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;

import static de.myrnet.springmightyconfig.config.DefaultConfig.ProductType.*;
import static de.myrnet.springmightyconfig.config.DefaultConfig.extractValue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConfigTest_Collective {

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

    @Test
    void overwrites() {
        var fifty = new BigDecimal("50.0");
        var icoString = "shop-v2.ico";
        var overwrites = Map.<String, Object>of(
                "ico-path", icoString,
                "shipping-cost", fifty
        );

        AppliedConfig appliedConfig = defaultConfig.getWithOverwrites(BIKES, overwrites);

        Assertions.assertAll(
                () -> assertEquals(fifty, appliedConfig.getShippingCost()),
                () -> assertEquals(icoString, appliedConfig.getIcoPath()),
                () -> assertThrows(
                        IllegalArgumentException.class,
                        () -> defaultConfig.getWithOverwrites(BIKES, Map.of("order.shipping-cost", fifty)),
                        "Non-existing property")
        );
    }

}
