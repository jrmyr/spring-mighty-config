package de.myrnet.springmightyconfig.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE, onConstructor_ = {@Autowired})
public class DefaultConfig {

    enum Type {
        BIKES,
        PARTS,
        CLOTHES,
        unsupported,
        ;
    }

    private ConfWebPage webPage;
    private ConfOrder order;

//    public AppliedConfig getConfigWithDefaults() {
//        AppliedConfig ac = new AppliedConfig(
//                //
//                //
//                //Hier das müssen wir irgendwie Typsicher machen - von beiden Seiten!!
//                //
//                //
//                confWebPage.getValues().get(ConfWebPage.MAIN_PAGE).get(),
//                confWebPage.getValues().get(ConfWebPage.URL_PATH).get(),
//                confOrder.getValues().get(ConfOrder.SHIPPING_COST).get(),
//                confOrder.getValues().get(ConfOrder.FREE_SHIPPING_LIMIT).get(),
//                confOrder.getValues().get(ConfOrder.DEFAULT_SHIPPING_COMPANY).get()
//        );
//        return ac;
//    }

    static <T> T extractValue(Type type, ConfigValue<T> configValue) {
        T value;
        switch (type) {
            case BIKES  : value = configValue.getBikes();   break;
            case PARTS  : value = configValue.getParts();   break;
            case CLOTHES: value = configValue.getClothes(); break;
            default: throw new IllegalArgumentException("The type '" + type + "' is unknown");
        }
        return Objects.requireNonNullElse(value, configValue.getStandard());
    }

}

