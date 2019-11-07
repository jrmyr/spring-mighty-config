package de.myrnet.springmightyconfig.config;

import lombok.*;

import java.math.BigDecimal;

/**
 * External representation to be used throughout the app; overrides of the different levels are already applied.
 */
@Getter
@Setter(value = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AppliedConfig {

    private DefaultConfig.ProductType productType;

    private String icoPath;
    private String urlPath;

    private BigDecimal shippingCost;
    private BigDecimal freeShippingLimit;
    private String defaultShippingCompany;

    AppliedConfig(DefaultConfig.ProductType productType) {
        this.productType = productType;
    }

    public static class AppliedConfigBuilder {

        public AppliedConfigBuilder shippingCostFromString(String shippingCost) {
            return this.shippingCost(new BigDecimal(shippingCost));
        }

        public AppliedConfigBuilder freeShippingLimitFromString(String freeShippingLimit) {
            return this.freeShippingLimit(new BigDecimal(freeShippingLimit));
        }

    }

}
