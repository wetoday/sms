package sms.sample;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SampleController {

    @Autowired
    SqlSession sqlSession;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String samplePage(Model model) {
        model.addAttribute("msg", "This is Sample Page.");
        model.addAttribute("DBVersion",
                sqlSession.selectOne("sms.sample.VersionMapper.getDBVersion"));
        return "sample";
    }
}
