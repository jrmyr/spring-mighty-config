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
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "config.web-page")
@Setter(value = AccessLevel.PACKAGE)
@Getter(value = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class WebPageGroup extends AbstractConfigGroup {

    private PropertyValue icoPath;
    private PropertyValue urlPath;

    protected Map<String, Pair<Function<String, AppliedConfigBuilder>, PropertyValue>> getActionMap(
            AppliedConfigBuilder appliedConfigBuilder) {
        return Map.of(
                "ico-path", Pair.of(appliedConfigBuilder::icoPath, icoPath),
                "url-path", Pair.of(appliedConfigBuilder::urlPath, urlPath)
        );
    }

}
