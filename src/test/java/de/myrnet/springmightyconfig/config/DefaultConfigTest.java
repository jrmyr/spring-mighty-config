package de.myrnet.springmightyconfig.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;

import static de.myrnet.springmightyconfig.config.DefaultConfig.ProductType.BIKES;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class DefaultConfigTest {

    @Autowired
    private DefaultConfig defaultConfig;

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
