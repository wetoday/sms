package sms.sample;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;

@Controller
public class SampleController {

    @Autowired
    private Environment env;

    @Autowired
    private SqlSession sqlSession;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String samplePage(Model model) {
        model.addAttribute("msg", "이것은 샘플 페이지 입니다.");
        model.addAttribute("DBVersion", getDBVersion());
        model.addAttribute("profile", getProfile());

        return "sample";
    }

    private String getDBVersion() {
        String dbVersion = sqlSession.selectOne("sms.sample.VersionMapper.getDBVersion");

        return dbVersion.contains("MariaDB") ? dbVersion : dbVersion + "-H2";
    }

    private String getProfile() {
        return Arrays.stream(env.getActiveProfiles())
                .filter(profile -> profile.equals("prod"))
                .map(profile -> "운영 환경(prod)")
                .findAny().orElse("개발 환경(dev)");
    }
}
