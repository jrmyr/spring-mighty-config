package de.myrnet.springmightyconfig.config.groups;

import de.myrnet.springmightyconfig.config.PropertyValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static de.myrnet.springmightyconfig.config.DefaultConfig.ProductType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrderGroupTest extends ConfigGroupTest {

    @Test
    void checkLoadedValues() {
        PropertyValue orderGroupShipping = defaultConfig.getOrderGroup().getDefaultShippingCompany();
        Assertions.assertAll(
                // Positive tests
                () -> assertEquals("Blue-Corp.", orderGroupShipping.get(BIKES),     "bikes type"),
                () -> assertEquals("YEL",        orderGroupShipping.get(PARTS),     "parts type"),
                () -> assertEquals("Packy",      orderGroupShipping.get(CLOTHES),   "clothes type"),
                // Negative tests
                () -> assertThrows(IllegalArgumentException.class,
                        () -> orderGroupShipping.get(unsupported), "unsupported product type")
        );
    }

}
