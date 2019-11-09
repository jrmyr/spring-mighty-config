package de.myrnet.springmightyconfig.config;

import de.myrnet.springmightyconfig.AppMain;
import de.myrnet.springmightyconfig.web.SimpleController;
import org.junit.jupiter.api.Test;
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

    @Test
    public void config_overwrite() throws Exception {

        var icoPath = "new_icon.png";

        this.mockMvc.perform(get("/conf/bikes?ico-path=" + icoPath))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.ico-path", is(icoPath)))
                ;
    }

}
