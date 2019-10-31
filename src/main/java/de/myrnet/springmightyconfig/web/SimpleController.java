package de.myrnet.springmightyconfig.web;

import de.myrnet.springmightyconfig.config.DefaultConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class SimpleController {

    @Autowired
    private DefaultConfig defaultConfig;

    @GetMapping(value = "conf", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity showConfig() {
        // With the current access modifiers, it does not really reveal that much...
        return ResponseEntity.ok(defaultConfig);
    }

}
