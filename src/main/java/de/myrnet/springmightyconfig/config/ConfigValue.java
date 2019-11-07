package de.myrnet.springmightyconfig.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Setter(value = AccessLevel.PACKAGE)
@Getter(value = AccessLevel.PACKAGE)
@ToString
public class ConfigValue<T> implements Cloneable {

    @NonNull
    private T standard;

    private Optional<T> bikes = Optional.empty();

    private Optional<T> parts = Optional.empty();

    private Optional<T> clothes = Optional.empty();

}


