package de.myrnet.springmightyconfig.config.groups;

import de.myrnet.springmightyconfig.config.AppliedConfig;
import de.myrnet.springmightyconfig.config.ConfigValue;
import de.myrnet.springmightyconfig.config.DefaultConfig.ProductType;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.function.Function;

@Configuration
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "config.web-page")
@Setter(value = AccessLevel.PACKAGE)
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class WebPage extends ConfigGroup {

    public static final String ICO_PATH = "ico-path";
    public static final String URL_PATH = "url-path";

    private ConfigValue<String> icoPath;
    private ConfigValue<String> urlPath;

    @Override
    public AppliedConfig doFillAppliedConfigBuilder(AppliedConfig appliedConfig, ProductType productType) {
        return appliedConfig.toBuilder()
                .icoPath(productType.extractValue(icoPath))
                .urlPath(productType.extractValue(urlPath))
                .build();
    }

    @Override
    protected Map<String, Function<String, AppliedConfig.AppliedConfigBuilder>> getPropertyFunctionMap(
            AppliedConfig.AppliedConfigBuilder appliedConfigBuilder) {
        return Map.of(
                ICO_PATH, appliedConfigBuilder::icoPath,
                URL_PATH, appliedConfigBuilder::urlPath
        );
    }

}
