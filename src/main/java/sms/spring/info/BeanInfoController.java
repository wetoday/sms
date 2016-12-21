package sms.spring.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Profile("!prod")
@Controller
public class BeanInfoController {

    @Autowired
    private BeanInfoService infoService;

    @RequestMapping(value = "/beans", method = RequestMethod.GET)
    public String showActiveBeans(ModelMap model) {
        model.addAttribute("rootBeans", infoService.getRootBeans());
        model.addAttribute("webBeans", infoService.getWebBeans());

        return "beans";
    }
}
