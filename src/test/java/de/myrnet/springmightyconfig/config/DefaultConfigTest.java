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
class DefaultConfigTest {

    @Autowired
    private DefaultConfig defaultConfig;

    @Test
    void get_ok() {
        Assertions.assertAll(
                () -> assertEquals("Blue-Corp.", defaultConfig.get(BIKES).getDefaultShippingCompany(),   "bikes type"),
                () -> assertEquals("YEL",        defaultConfig.get(PARTS).getDefaultShippingCompany(),   "parts type"),
                () -> assertEquals("Packy",      defaultConfig.get(CLOTHES).getDefaultShippingCompany(), "clothes type"),

                () -> assertEquals("/parts",   defaultConfig.get(PARTS).getUrlPath(),   "URL parts"),
                () -> assertEquals("/clothes", defaultConfig.get(CLOTHES).getUrlPath(), "URL clothes")
        );
    }

    @Test
    void get_fail() {
        Assertions.assertAll(
                () -> assertThrows(IllegalArgumentException.class,
                        () -> defaultConfig.get(unsupported), "unsupported product type")
        );
    }

    @Test
    void getWithOverrides() {
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
