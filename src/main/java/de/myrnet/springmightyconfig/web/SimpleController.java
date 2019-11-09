package de.myrnet.springmightyconfig.web;

import de.myrnet.springmightyconfig.config.AppliedConfig;
import de.myrnet.springmightyconfig.config.DefaultConfig;
import de.myrnet.springmightyconfig.config.DefaultConfig.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import static de.myrnet.springmightyconfig.config.DefaultConfig.transformMapValues;

@RestController("")
public class SimpleController {

    @Autowired
    private DefaultConfig defaultConfig;

    @GetMapping(value = "/conf/{productType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity showConfig(@PathVariable("productType") String productTypeStr, WebRequest request) {
        ProductType productType = ProductType.valueOf(productTypeStr.toUpperCase());

        AppliedConfig appliedConfig = defaultConfig.getWithOverrides(productType,
                transformMapValues(request.getParameterMap()));

        return ResponseEntity.ok(appliedConfig);
    }

}
