package de.myrnet.springmightyconfig.config;

import de.myrnet.springmightyconfig.AppMain;
import de.myrnet.springmightyconfig.web.SimpleController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SimpleController.class)
@ComponentScan(basePackageClasses={AppMain.class})
class DefaultConfigWebOverrideTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvSource({
            "ico-path, new_icon.png",
            "default-shipping-company, self-taker"
    })
    public void configOverwritesString(String propName, String overwriteValue) throws Exception {
        doCheck(propName, overwriteValue);
    }

    @ParameterizedTest
    @ValueSource(strings = {"shipping-cost", "free-shipping-limit"})
    public void configOverwritesNumber(String propName) throws Exception {
        doCheck(propName, 123.45);
    }

    private void doCheck(String propName, Object overwriteValue) throws Exception {
        this.mockMvc.perform(get("/conf/bikes?" + propName + "=" + overwriteValue))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$." + propName, is(overwriteValue)))
        ;
    }

    @Test
    public void multipleConfigOverwrites() throws Exception {

        String icoPath = "new_icon.png";
        double shippingCost = 3.95;
        String queryParams = String.format("ico-path=%s&shipping-cost=%s", icoPath, shippingCost);

        this.mockMvc.perform(get("/conf/bikes?" + queryParams))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.ico-path", is(icoPath)))
                .andExpect(jsonPath("$.shipping-cost", is(shippingCost)))
        ;
    }

}
