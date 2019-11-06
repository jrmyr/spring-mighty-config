package de.myrnet.springmightyconfig.config;

import lombok.*;
import org.apache.commons.lang3.tuple.Pair;

import java.math.BigDecimal;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static de.myrnet.springmightyconfig.config.OrderConf.*;
import static de.myrnet.springmightyconfig.config.WebPageConf.ICO_PATH;
import static de.myrnet.springmightyconfig.config.WebPageConf.URL_PATH;

/**
 * External representation to be used throughout the app; overwrites of the different levels are already applied.
 */
@Getter
@Setter(value = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@AllArgsConstructor
public class AppliedConfig {

//    private final DefaultConfig defaultConfig;

    private DefaultConfig.ProductType productType;

    private String icoPath;
    private String urlPath;

    private BigDecimal shippingCost;
    private BigDecimal freeShippingLimit;
    private String defaultShippingCompany;

    @Getter(value = AccessLevel.PACKAGE)
//    private Map<String, Supplier<ConfigValue<?>>> values;
    private Map<String, Pair<Supplier<?>, Consumer<?>>> values;

    public void setValues() {
        values = Map.of(
                ICO_PATH, Pair.of(this::getIcoPath, (Consumer<String>) this::setIcoPath),
                URL_PATH, Pair.of(this::getUrlPath, (Consumer<String>) this::setUrlPath),

                SHIPPING_COST, Pair.of(this::getShippingCost, (Consumer<BigDecimal>) this::setShippingCost),
                FREE_SHIPPING_LIMIT, Pair.of(this::getFreeShippingLimit, (Consumer<BigDecimal>) this::setFreeShippingLimit),
                DEFAULT_SHIPPING_COMPANY, Pair.of(this::getDefaultShippingCompany, (Consumer<String>) this::setDefaultShippingCompany)

        );
    }

    @SuppressWarnings("unchecked")
    <T> T getByPropName(String propName) {
        return (T) values.get(propName).getLeft().get();
    }

    @SuppressWarnings("unchecked")
    <T> void setByPropName(String propName, T value) {
        if (! values.containsKey(propName)) {
            throw new IllegalArgumentException(String.format(
                    "property '%s' is not existing in the property list: %s", propName, values.keySet()));
        }
        var consumer = (Consumer<T>) values.get(propName).getRight();
        consumer.accept(value);
    }


//    private void setIcoPath(String icoPath) {
//        this.icoPath = (String) icoPath;
//    }
//
//    private void setUrlPath(Object urlPath) {
//        this.urlPath = (String) urlPath;
//    }
//
//    private void setShippingCost(Object shippingCost) {
//        this.shippingCost = (BigDecimal) shippingCost;
//    }
//
//    private void setFreeShippingLimit(Object freeShippingLimit) {
//        this.freeShippingLimit = (BigDecimal) freeShippingLimit;
//    }
//
//    private void setDefaultShippingCompany(Object defaultShippingCompany) {
//        this.defaultShippingCompany = (String) defaultShippingCompany;
//    }
}
