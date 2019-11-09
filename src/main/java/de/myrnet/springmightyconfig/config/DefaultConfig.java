package de.myrnet.springmightyconfig.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.myrnet.springmightyconfig.config.groups.OrderGroup;
import de.myrnet.springmightyconfig.config.groups.WebPageGroup;
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

import static java.util.stream.Collectors.toMap;

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
    }

    @JsonProperty("web-page")
    private WebPageGroup webPageGroup;

    @JsonProperty("order")
    private OrderGroup orderGroup;

    public AppliedConfig get(ProductType productType) {
        AppliedConfig ac = new AppliedConfig(productType);
        ac = orderGroup.fillAppliedConfigBuilder(ac);
        ac = webPageGroup.fillAppliedConfigBuilder(ac);
        return ac;
    }

    public static Map<String, String> transformMapValues(Map<String, String[]> map) {
        return map.entrySet().stream().collect(toMap(Map.Entry::getKey, e -> String.join(",", e.getValue())));
    }

    public AppliedConfig getWithOverrides(ProductType productType, Map<String, String> overrides) {
        AppliedConfig ac = get(productType);

        Set<String> props = Stream.of(orderGroup.getAvailableConfigKeys(), webPageGroup.getAvailableConfigKeys())
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        System.out.println(props);

        ac = orderGroup.overrideValues(ac, overrides);
        ac = webPageGroup.overrideValues(ac, overrides);

        return ac;
    }

}

