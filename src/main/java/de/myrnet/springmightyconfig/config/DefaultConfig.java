package de.myrnet.springmightyconfig.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * Internal (package-wide) representation retaining the config's grouping (to be checked if really needed/wanted)
 */
@Component
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = {@Autowired})
public class DefaultConfig {

    enum ProductType {
        BIKES,
        PARTS,
        CLOTHES,
        unsupported,
        ;
    }

    @JsonProperty("web-page")
    private WebPageConf webPageConf;

    @JsonProperty("order")
    private OrderConf orderConf;

    public AppliedConfig getWithOverwrites(ProductType productType, Map<String, Object> overwrites) {
        AppliedConfig ac = getAppliedConfigWithDefaults(productType);
        return overWriteValues(ac, overwrites);
    }

    private AppliedConfig getAppliedConfigWithDefaults(ProductType productType) {
        AppliedConfig ac = AppliedConfig.builder()
                .productType(productType)
                .icoPath(extractValue(productType, webPageConf.getIcoPath()))
                .urlPath(extractValue(productType, webPageConf.getUrlPath()))
                .shippingCost(extractValue(productType, orderConf.getShippingCost()))
                .freeShippingLimit(extractValue(productType, orderConf.getFreeShippingLimit()))
                .defaultShippingCompany(extractValue(productType, orderConf.getDefaultShippingCompany()))
                .build();
        ac.setValues();
        return ac;
    }

    private AppliedConfig overWriteValues(AppliedConfig appliedConfig, Map<String, Object> overwrites)  {
        overwrites.forEach( (k, v) -> {
            appliedConfig.setByPropName(k, v);
        });
        return appliedConfig;
    }

    static <T> T extractValue(ProductType productType, ConfigValue<T> configValue) {
        T value;
        switch (productType) {
            case BIKES  : value = configValue.getBikes();   break;
            case PARTS  : value = configValue.getParts();   break;
            case CLOTHES: value = configValue.getClothes(); break;
            default: throw new IllegalArgumentException("The type '" + productType + "' is unknown");
        }
        return Objects.requireNonNullElse(value, configValue.getStandard());
    }

}

