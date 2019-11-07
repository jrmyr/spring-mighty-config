package de.myrnet.springmightyconfig.config;

import de.myrnet.springmightyconfig.config.groups.Order;
import de.myrnet.springmightyconfig.config.groups.WebPage;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Internal (package-wide) representation retaining the config's grouping (to be checked if really needed/wanted)
 */
@Component
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = {@Autowired})
public class DefaultConfig {

    public enum ProductType {
        BIKES,
        PARTS,
        CLOTHES,
        unsupported,
        ;

        public <T> T extractValue(ConfigValue<T> configValue) {
            Optional<T> value;
            switch (this) {
                case BIKES  : value = configValue.getBikes(); break;
                case PARTS  : value = configValue.getParts(); break;
                case CLOTHES: value = configValue.getClothes(); break;
                default: throw new IllegalArgumentException("The type '" + this + "' is unknown");
            }
            return value.orElse(configValue.getStandard());
        }

    }

    //@JsonProperty("web-page")
    private WebPage webPage;

    //@JsonProperty("order")
    private Order order;

    public AppliedConfig getWithOverrides(ProductType productType, Map<String, String> overrides) {
        AppliedConfig ac = new AppliedConfig(productType);

        Set<String> props = Stream.of(order.getAvailableConfigKeys(), webPage.getAvailableConfigKeys())
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        System.out.println(props);

        ac = order.fillAppliedConfigBuilder(ac);
        ac = order.overrideValues(ac, overrides);

        ac = webPage.fillAppliedConfigBuilder(ac);
        ac = webPage.overrideValues(ac, overrides);

        return ac;
    }

}

