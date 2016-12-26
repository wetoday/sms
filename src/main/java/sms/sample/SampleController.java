package sms.sample;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Arrays;
import java.util.Locale;

@Controller
public class SampleController {

    @Autowired
    private Environment env;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private SqlSession sqlSession;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String samplePage(Locale locale, Model model) {
        model.addAttribute("DBVersion", getDBVersion());
        model.addAttribute("profile", getProfile(locale));

        return "sample/main";
    }

    private String getDBVersion() {
        String dbVersion = sqlSession.selectOne("sms.sample.VersionMapper.getDBVersion");

        return dbVersion.contains("MariaDB") ? dbVersion : dbVersion + "-H2";
    }

    private String getProfile(final Locale locale) {
        return Arrays.stream(env.getActiveProfiles())
                .filter(profile -> profile.equals("prod"))
                .map(profile -> messageSource.getMessage("sample.profile.prod", null, locale))
                .findAny().orElse(messageSource.getMessage("sample.profile.dev", null, locale));
    }
}