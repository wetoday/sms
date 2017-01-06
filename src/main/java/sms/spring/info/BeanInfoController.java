package sms.spring.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BeanInfoController {

    private final BeanInfoService infoService;

    @Autowired
    public BeanInfoController(BeanInfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/beans")
    public String showActiveBeans(ModelMap model) {
        model.addAttribute("rootBeans", infoService.getRootBeans());
        model.addAttribute("webBeans", infoService.getWebBeans());

        return "spring/beans";
    }
}