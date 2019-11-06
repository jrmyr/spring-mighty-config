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
public class OrderConf {

    public static final String SHIPPING_COST = "shipping-cost";
    public static final String FREE_SHIPPING_LIMIT = "free-shipping-limit";
    public static final String DEFAULT_SHIPPING_COMPANY = "default-shipping-company";

    private ConfigValue<BigDecimal> shippingCost;
    private ConfigValue<BigDecimal> freeShippingLimit;
    private ConfigValue<String> defaultShippingCompany;

    @Getter(value = AccessLevel.PACKAGE)
    private Map<String, Supplier<ConfigValue<?>>> values;

    public OrderConf() {
        values = Map.of(
                SHIPPING_COST, this::getShippingCost,
                FREE_SHIPPING_LIMIT, this::getFreeShippingLimit,
                DEFAULT_SHIPPING_COMPANY, this::getDefaultShippingCompany
        );
    }

}
