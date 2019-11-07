package de.myrnet.springmightyconfig.config.groups;

import de.myrnet.springmightyconfig.config.AppliedConfig;
import de.myrnet.springmightyconfig.config.AppliedConfig.AppliedConfigBuilder;
import de.myrnet.springmightyconfig.config.ConfigValue;
import de.myrnet.springmightyconfig.config.DefaultConfig;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Function;

@Configuration
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "config.order")
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Order extends ConfigGroup {

    public static final String SHIPPING_COST = "shipping-cost";
    public static final String FREE_SHIPPING_LIMIT = "free-shipping-limit";
    public static final String DEFAULT_SHIPPING_COMPANY = "default-shipping-company";

    private ConfigValue<BigDecimal> shippingCost;
    private ConfigValue<BigDecimal> freeShippingLimit;
    private ConfigValue<String> defaultShippingCompany;

    @Override
    protected AppliedConfig doFillAppliedConfigBuilder(AppliedConfig appliedConfig, DefaultConfig.ProductType productType) {
        return appliedConfig.toBuilder()
                .shippingCost(productType.extractValue(shippingCost))
                .freeShippingLimit(productType.extractValue(freeShippingLimit))
                .defaultShippingCompany(productType.extractValue(defaultShippingCompany))
                .build();
    }

    @Override
    protected Map<String, Function<String, AppliedConfigBuilder>> getPropertyFunctionMap(
            AppliedConfigBuilder appliedConfigBuilder) {
        return Map.of(
                SHIPPING_COST, appliedConfigBuilder::shippingCostFromString,
                FREE_SHIPPING_LIMIT, appliedConfigBuilder::freeShippingLimitFromString,
                DEFAULT_SHIPPING_COMPANY, appliedConfigBuilder::defaultShippingCompany
        );
    }

}
