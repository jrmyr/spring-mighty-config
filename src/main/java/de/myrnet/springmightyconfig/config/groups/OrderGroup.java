package de.myrnet.springmightyconfig.config.groups;

import de.myrnet.springmightyconfig.config.AppliedConfig.AppliedConfigBuilder;
import de.myrnet.springmightyconfig.config.PropertyValue;
import lombok.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Function;

@Configuration
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "config.order")
@Setter(value = AccessLevel.PACKAGE)
@Getter(value = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class OrderGroup extends AbstractConfigGroup {

    private PropertyValue shippingCost;
    private PropertyValue freeShippingLimit;
    private PropertyValue defaultShippingCompany;

    @Override
    protected Map<String, Pair<Function<String, AppliedConfigBuilder>, PropertyValue>> getActionMap(
            AppliedConfigBuilder appliedConfigBuilder) {
        return Map.of(
                "shipping-cost", Pair.of(appliedConfigBuilder::shippingCostFromString, shippingCost),
                "free-shipping-limit", Pair.of(appliedConfigBuilder::freeShippingLimitFromString, freeShippingLimit),
                "default-shipping-company", Pair.of(appliedConfigBuilder::defaultShippingCompany, defaultShippingCompany)
        );
    }

}
