package de.myrnet.springmightyconfig.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Supplier;

@Configuration
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "config.order")
@Setter
@Getter(value = AccessLevel.PACKAGE)
@ToString
//@Builder(toBuilder = true)
@AllArgsConstructor
public class ConfOrder {

    public static final String SHIPPING_COST = "shipping-cost";
    public static final String FREE_SHIPPING_LIMIT = "free-shipping-limit";
    public static final String DEFAULT_SHIPPING_COMPANY = "default-shipping-company";

    @Getter(value = AccessLevel.PUBLIC)
    private Map<String, Supplier<ConfigValue<?>>> values;

    public ConfOrder() {
        values = Map.of(
                SHIPPING_COST, this::getShippingCost,
                FREE_SHIPPING_LIMIT, this::getFreeShippingLimit,
                DEFAULT_SHIPPING_COMPANY, this::getDefaultShippingCompany
        );
    }

    @PostConstruct
    public void initCheck() {
        System.out.println("Initialized? - " + values.get(SHIPPING_COST).get());
    }

    private ConfigValue<BigDecimal> shippingCost;
    private ConfigValue<BigDecimal> freeShippingLimit;
    private ConfigValue<String> defaultShippingCompany;

}
