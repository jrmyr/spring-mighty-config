package de.myrnet.springmightyconfig.config.groups;

import de.myrnet.springmightyconfig.config.PropertyValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static de.myrnet.springmightyconfig.config.DefaultConfig.ProductType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class WebPageGroupTest extends ConfigGroupTest {

    @Test
    void checkLoadedValues() {
        PropertyValue webPageGroupUrlPath = defaultConfig.getWebPageGroup().getUrlPath();
        Assertions.assertAll(
                // Positive tests
                () -> assertEquals("/parts",     webPageGroupUrlPath.get(PARTS),   "URL parts"),
                () -> assertEquals("/clothes",   webPageGroupUrlPath.get(CLOTHES), "URL clothes"),
                // Negative tests
                () -> assertThrows(IllegalArgumentException.class,
                        () -> webPageGroupUrlPath.get(unsupported), "unsupported product type")
        );
    }

}
