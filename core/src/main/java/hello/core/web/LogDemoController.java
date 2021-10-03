package hello.core.web;

import hello.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Provider;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
    // request scope 는 HTTP 요청이 있을때 하나씩 생성된다 -> 애플리케이션 실행 시점에 생성되어있지 않다.
    // ObjectProvider 를 사용해서 DL 하면 된다.
    // 또는 Scope 에 proxyMode 를 추가한다. -> 가짜 myLogger 를 우선 만든다 -> 실제 기능을 요청할때, 진짜를 찾는다.
    private final MyLogger myLogger;
//    private final ObjectProvider<MyLogger> myLoggerProvider;


    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURL = request.getRequestURL().toString();
//        MyLogger myLogger = myLoggerProvider.getObject();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }

}
