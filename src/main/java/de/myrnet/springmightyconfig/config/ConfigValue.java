package de.myrnet.springmightyconfig.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Setter(value = AccessLevel.PACKAGE)
@Getter(value = AccessLevel.PACKAGE)
@ToString
public class ConfigValue<T> implements Cloneable {

    @NonNull
    private T standard;

    private T bikes;

    private T parts;

    private T clothes;

}


