package app.spring.info;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.hasItemInArray;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class BeanInfoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BeanInfoService beanInfoService;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(new BeanInfoController(beanInfoService)).build();
    }

    @Test
    public void showActiveBeans() throws Exception {
        given(this.beanInfoService.getRootBeans())
                .willReturn(new String[]{"org.springframework.context.event.internalEventListenerFactory"});
        given(this.beanInfoService.getWebBeans())
                .willReturn(new String[]{"org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter"});

        mockMvc.perform(get("/beans"))
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(BeanInfoController.class))
                .andExpect(handler().methodName(("showActiveBeans")))
                .andExpect(model().attribute("rootBeans",
                        hasItemInArray("org.springframework.context.event.internalEventListenerFactory")))
                .andExpect(model().attribute("webBeans",
                        hasItemInArray("org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter")))
                .andExpect(view().name("spring/beans"));
    }
}