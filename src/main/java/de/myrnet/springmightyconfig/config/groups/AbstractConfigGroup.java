package de.myrnet.springmightyconfig.config.groups;

import de.myrnet.springmightyconfig.config.AppliedConfig;
import de.myrnet.springmightyconfig.config.AppliedConfig.AppliedConfigBuilder;
import de.myrnet.springmightyconfig.config.PropertyValue;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

public abstract class AbstractConfigGroup {

    public AppliedConfig fillAppliedConfigBuilder(AppliedConfig appliedConfig) {
        assertProductTypeExisting(appliedConfig);

        AppliedConfigBuilder appliedConfigBuilder = appliedConfig.toBuilder();
        getActionMap(appliedConfigBuilder).values()
                .forEach(pair -> pair.getLeft().apply(pair.getRight().get(appliedConfig.getProductType())));

        return appliedConfigBuilder.build();
    }

    public AppliedConfig overrideValues(AppliedConfig appliedConfig, Map<String, String> overrides) {
        assertProductTypeExisting(appliedConfig);

        AppliedConfigBuilder appliedConfigBuilder = appliedConfig.toBuilder();
        Map<String, Pair<Function<String, AppliedConfigBuilder>, PropertyValue>> funcMap =
                getActionMap(appliedConfigBuilder);
        overrides.forEach((prop, value) -> {
            if (funcMap.containsKey(prop)) {
                funcMap.get(prop).getLeft().apply(value);
            }
        });

        return appliedConfigBuilder.build();
    }

    protected abstract Map<String, Pair<Function<String, AppliedConfigBuilder>, PropertyValue>> getActionMap(
            AppliedConfigBuilder appliedConfig);

    private void assertProductTypeExisting(AppliedConfig appliedConfig) {
        Assert.notNull(appliedConfig.getProductType(), "Product type must be defined");
    }

    public Set<String> getAvailableConfigKeys() {
        return getActionMap(AppliedConfig.builder()).keySet();
    }
}
