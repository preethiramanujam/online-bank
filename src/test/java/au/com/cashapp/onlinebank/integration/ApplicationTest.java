package au.com.cashapp.onlinebank.integration;

import au.com.cashapp.onlinebank.App;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = App.class)
@AutoConfigureMockMvc
public class ApplicationTest {

    @Test
    public void springApplicationRun() {
        //standup springboot!
    }
}


