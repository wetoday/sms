package sms.spring.info;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class BeanInfoService {

    @Autowired
    private ApplicationContext webContext;

    String[] getRootBeans() {
        return getBeansOf(webContext.getParent());
    }

    String[] getWebBeans() {
        return getBeansOf(webContext);
    }

    private String[] getBeansOf(ApplicationContext context) {
        return sort(context.getBeanDefinitionNames());
    }

    private String[] sort(String[] beans) {
        Stream<String> longNameBeans = Stream.of(beans)
                .filter(bean -> bean.contains("org.springframework"))
                .sorted(String::compareToIgnoreCase);

        Stream<String> shortNameBeans = Stream.of(beans)
                .filter(bean -> !bean.contains("org.springframework"))
                .sorted(String::compareToIgnoreCase);

        return Stream.concat(shortNameBeans, longNameBeans).toArray(String[]::new);
    }
}