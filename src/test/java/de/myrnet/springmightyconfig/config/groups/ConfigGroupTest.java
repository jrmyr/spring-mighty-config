package de.myrnet.springmightyconfig.config.groups;

import de.myrnet.springmightyconfig.config.DefaultConfig;
import org.springframework.beans.factory.annotation.Autowired;

abstract class ConfigGroupTest {

    @Autowired
    protected DefaultConfig defaultConfig;

    abstract void checkLoadedValues();

}
