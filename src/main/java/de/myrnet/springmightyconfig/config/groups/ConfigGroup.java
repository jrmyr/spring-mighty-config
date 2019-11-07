package de.myrnet.springmightyconfig.config.groups;

import de.myrnet.springmightyconfig.config.AppliedConfig;
import de.myrnet.springmightyconfig.config.AppliedConfig.AppliedConfigBuilder;
import de.myrnet.springmightyconfig.config.DefaultConfig.ProductType;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public abstract class ConfigGroup {

    public AppliedConfig fillAppliedConfigBuilder(AppliedConfig appliedConfig) {
        assertProductTypeExisting(appliedConfig);
        return doFillAppliedConfigBuilder(appliedConfig, appliedConfig.getProductType());
    }

    protected abstract AppliedConfig doFillAppliedConfigBuilder(AppliedConfig appliedConfig, ProductType productType);

    public AppliedConfig overrideValues(AppliedConfig appliedConfig, Map<String, String> overrides) {
        assertProductTypeExisting(appliedConfig);
        AppliedConfigBuilder appliedConfigBuilder = appliedConfig.toBuilder();

        Map<String, Function<String, AppliedConfigBuilder>> funcMap = getPropertyFunctionMap(appliedConfigBuilder);
        overrides.forEach((prop, value) -> {
            if (funcMap.containsKey(prop)) {
                funcMap.get(prop).apply(value);
            }
        });

        return appliedConfigBuilder.build();
    }

    protected abstract Map<String, Function<String, AppliedConfigBuilder>> getPropertyFunctionMap(
            AppliedConfigBuilder appliedConfig);

    private void assertProductTypeExisting(AppliedConfig appliedConfig) {
        Assert.isTrue(appliedConfig.getProductType() != null, "Can only fill values when product type is defined");
    }

    public Set<String> getAvailableConfigKeys() {
        return getPropertyFunctionMap(AppliedConfig.builder()).keySet();
    }
}
