package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // 지정된 패키지 하위 패키지를 모두 탐색한다. (지정 안해주면 모든 파일을 다 확인하기 떄문에 오래걸린다.)
//        basePackages = "hello.core",
        // 지정한 클래스의 패키지를 탐색 시작 위치로 지정한다.
//        basePackageClasses = AutoAppConfig.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class) //AppConfig 제외
        // 원래 이렇게 안해도 되는데 이전의 예제코드를 남겨놓기 위해서 필요한 부분
) // @Component Annotation 이 붙은 클래스 스캔해서 스프링 빈으로 등록한다.
public class AutoAppConfig {
    // basePackages, basePackageClasses 를 지정하지 않은 경우
    // @ComponentScan 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
    // 권장 하는 방법 : 패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 둔다.
}

