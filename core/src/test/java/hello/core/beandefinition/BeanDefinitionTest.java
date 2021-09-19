package hello.core.beandefinition;

import hello.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {

    // 그냥 ApplicationContext 로 하면 getBeanDefinition() 이 정의가 안되어있음
    AnnotationConfigApplicationContext javaAc = new AnnotationConfigApplicationContext(AppConfig.class);
    GenericXmlApplicationContext xmlAc = new GenericXmlApplicationContext("appConfig.xml");

    // factory method 를 사용
    @Test
    @DisplayName("Java 빈 설정 메타정보 확인")
    void findApplicationJavaBean() {
        String[] beanDefinitionNames = javaAc.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = javaAc.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName + " beanDefinition = " + beanDefinition);
            }
        }
    }

    // Bean 을 직접 등록
    @Test
    @DisplayName("Xml 빈 설정 메타정보 확인")
    void findApplicationXmlBean() {
        String[] beanDefinitionNames = xmlAc.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = xmlAc.getBeanDefinition(beanDefinitionName);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName + " beanDefinition = " + beanDefinition);
            }
        }
    }
}
