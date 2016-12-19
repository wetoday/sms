package sms.spring.info;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasItemInArray;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextHierarchy({
        @ContextConfiguration("file:src/main/webapp/WEB-INF/spring-config/root-context.xml"),
        @ContextConfiguration("file:src/main/webapp/WEB-INF/spring-config/web-context.xml")
})
public class BeanInfoControllerTest {

    @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void showActiveBeans() throws Exception {
        mockMvc.perform(get("/beans"))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(BeanInfoController.class))
                .andExpect(handler().methodName(("showActiveBeans")))
                .andExpect(model().attribute("rootBeans",
                        hasItemInArray("org.springframework.context.event.internalEventListenerFactory")))
                .andExpect(model().attribute("webBeans",
                        hasItemInArray("org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter")))
                .andExpect(view().name("beans"));
    }
}