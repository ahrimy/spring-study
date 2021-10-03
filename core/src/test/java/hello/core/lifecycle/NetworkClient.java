package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// 초기화, 소멸 인터페이스
// 단점
// 스프링 전용 인터페이스이다. 해당 코드가 스프링 전용 인터페이스에 의존한다.
// 초기화, 소멸 메서드의 이름을 변경할 수 없다.
// 내가 코드를 고칠 수 없는 외부 라이브러리에 적용할 수 없다.
// => 거의 사용하지 않는다.
//public class NetworkClient implements InitializingBean, DisposableBean {
public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
        connect();
        call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message: " + message);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }
    // @PostConstruct, @PreDestroy : 최신 스프링에서 가장 권장하는 방법
    // 외부 라이브러리에는 적용하지 못한다.
    // 그때는  @Bean 의 기능을 사용해야한다.
    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }
    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }

    /*
    // 초기화, 소멸 인터페이스
    // 의존관계 주입이 끝나면 실행됨
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    }

    // 컨테이더가 종료되기 전
    @Override
    public void destroy() throws Exception {
        System.out.println("NetworkClient.destroy");
        disconnect();
    }
     */
}
