package de.myrnet.springmightyconfig.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.function.Supplier;

@Configuration
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "config.web-page")
@Setter
@Getter(value = AccessLevel.PACKAGE)
@ToString
//@Builder(toBuilder = true)
@AllArgsConstructor
public class WebPageConf {

    public static final String ICO_PATH = "ico-path";
    public static final String URL_PATH = "url-path";

    private ConfigValue<String> icoPath;
    private ConfigValue<String> urlPath;

    @Getter(value = AccessLevel.PACKAGE)
    private Map<String, Supplier<ConfigValue<?>>> values;

    public WebPageConf() {
        values = Map.of(
                ICO_PATH, this::getIcoPath,
                URL_PATH, this::getUrlPath
        );
    }

}
