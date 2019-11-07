package de.myrnet.springmightyconfig.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;

import static de.myrnet.springmightyconfig.config.DefaultConfig.ProductType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ConfigTest_Collective {

    @Autowired
    private DefaultConfig defaultConfig;

    @Test
    void getValue_allTypes() {
        ConfigValue<String> webPageConfUrlPath = defaultConfig.getWebPage().getUrlPath();
        ConfigValue<String> orderConfShiping = defaultConfig.getOrder().getDefaultShippingCompany();
        Assertions.assertAll(
                // Positive tests
                () -> assertEquals("/parts",     PARTS.extractValue(webPageConfUrlPath),   "URL parts"),
                () -> assertEquals("/clothes",   CLOTHES.extractValue(webPageConfUrlPath), "URL clothes"),
                () -> assertEquals("Blue-Corp.", BIKES.extractValue(orderConfShiping),     "bikes type"),
                () -> assertEquals("YEL",        PARTS.extractValue(orderConfShiping),     "parts type"),
                () -> assertEquals("Packy",      CLOTHES.extractValue(orderConfShiping),   "clothes type"),
                // Negative tests
                () -> assertThrows(IllegalArgumentException.class,
                        () -> unsupported.extractValue(orderConfShiping), "order conf error"),
                () -> assertThrows(IllegalArgumentException.class,
                        () -> unsupported.extractValue(webPageConfUrlPath), "web page conf error")
        );
    }

    @Test
    void overrides() {
        var fiftyString = "50.0";
        var newIcoString = "shop-v2.ico";
        var overrides = Map.of(
                "ico-path", newIcoString,
                "shipping-cost", fiftyString
        );

        AppliedConfig appliedConfig = defaultConfig.getWithOverrides(BIKES, overrides);

        Assertions.assertAll(
                () -> assertEquals(new BigDecimal(fiftyString), appliedConfig.getShippingCost()),
                () -> assertEquals(newIcoString, appliedConfig.getIcoPath())
//                ,
//                () -> assertThrows(
//                        IllegalArgumentException.class,
//                        () -> defaultConfig.getWithOverrides(BIKES, Map.of("order.shipping-cost", fiftyString)),
//                        "Non-existing property")
        );
    }

}
