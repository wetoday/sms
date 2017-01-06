package sms.sample;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.Locale;

@Controller
public class HomeController {

    private final Environment env;
    private final MessageSource messageSource;
    private final SqlSession sqlSession;

    @Autowired
    public HomeController(Environment env, MessageSource messageSource, SqlSession sqlSession) {
        this.env = env;
        this.messageSource = messageSource;
        this.sqlSession = sqlSession;
    }

    @GetMapping("/")
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