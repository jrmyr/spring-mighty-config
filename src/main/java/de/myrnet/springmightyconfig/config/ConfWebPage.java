package de.myrnet.springmightyconfig.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Configuration
@ConfigurationProperties(ignoreUnknownFields = false, prefix = "config.web")
@Setter
@Getter(value = AccessLevel.PACKAGE)
@ToString
//@Builder(toBuilder = true)
@AllArgsConstructor
public class ConfWebPage {

    public static final String ICO_PATH = "ico-path";
    public static final String URL_PATH = "url-path";

    @Getter(value = AccessLevel.PUBLIC)
    private Map<String, Supplier<ConfigValue<?>>> values;

    public ConfWebPage() {
        values = Map.of(
                ICO_PATH, this::getIcoPath,
                URL_PATH, this::getUrlPath
        );
    }

    @PostConstruct
    public void initCheck() {
        System.out.println("Initialized? - " + values.get(ICO_PATH).get());
    }

    private ConfigValue<String> icoPath;
    private ConfigValue<String> urlPath;

}
