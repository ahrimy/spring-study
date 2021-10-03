package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);

        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        // 설정 정보 사용하여 빈 생명주기 콜백 설정
        // 메서드 이름 자유롭게 설정 가능, 스프링 빈이 스프링 코드에 의존하지 않음
        // 코드가 아니라 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료, 메서드를 적용할 수 있다.
        // destroyMethod 의 default 는 (inferred) 이다 => close, shutdown 라는 이름의 메서드를 자동으로 호출해준다.
        // @Bean 으로 등록하면 종료 메서드는 따로 적어주지 않아도 동작한다.
        //@Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("https://hello-spring.dev");
            return networkClient;
        }
    }
}
