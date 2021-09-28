package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) //AppConfig 제외
        // 원래 이렇게 안해도 되는데 이전의 예제코드를 남겨놓기 위해서 필요한 부분
) // @Component Annotation 이 붙은 클래스 스캔해서 스프링 빈으로 등록한다.
public class AutoAppConfig {

}
