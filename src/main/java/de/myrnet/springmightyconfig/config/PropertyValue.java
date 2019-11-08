package de.myrnet.springmightyconfig.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.Optional;

@Setter(value = AccessLevel.PACKAGE)
@Getter(value = AccessLevel.PACKAGE)
@ToString
public class PropertyValue {

    @NonNull
    protected String standard;
    protected Optional<String> bikes = Optional.empty();
    protected Optional<String> parts = Optional.empty();
    protected Optional<String> clothes = Optional.empty();

    public String get(DefaultConfig.ProductType productType) {
        Optional<String> value;
        switch (productType) {
            case BIKES  : value = getBikes(); break;
            case PARTS  : value = getParts(); break;
            case CLOTHES: value = getClothes(); break;
            default: throw new IllegalArgumentException("The type '" + productType + "' is unknown");
        }
        return value.orElse(getStandard());
    }

}
