package site.metacoding.finals.handler;

import java.security.Principal;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import site.metacoding.finals.config.auth.PrincipalUser;
import site.metacoding.finals.config.exception.RuntimeApiException;
import site.metacoding.finals.domain.customer.CustomerRepository;
import site.metacoding.finals.domain.shop.Shop;
import site.metacoding.finals.domain.shop.ShopRepository;

@RequiredArgsConstructor
@Component
@Aspect
public class VerifyAdvice {

    private final CustomerRepository customerRepository;
    private final ShopRepository shopRepository;

    @Pointcut("@annotation(site.metacoding.finals.config.annotation.VerifyCustomer)")
    public void cutCustomer() {

    }

    @Pointcut("@annotation(site.metacoding.finals.config.annotation.VerifyShop)")
    public void cutShop() {

    }

    @Before(value = "cutCustomer()")
    public void verifyCustomer(JoinPoint joinpoint) throws Throwable {
        System.out.println("AOP 실행됨");

        Object args = joinpoint.getArgs()[0];
        PrincipalUser user = (PrincipalUser) args;
        System.out.println(user.getUsername());
        System.out.println(user.getId());

        customerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeApiException("잘못된 유저 요청입니다", HttpStatus.NOT_FOUND));

    }

    @Around(value = "cutShop()")
    public void verifyCustomer(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("AOP 실행됨");

        Object args = proceedingJoinPoint.getArgs()[0];
        PrincipalUser user = (PrincipalUser) args;
        System.out.println(user.getUsername());
        System.out.println(user.getId());

        Shop args2 = (Shop) proceedingJoinPoint.getArgs()[1];
        // customerRepository.findByUserId(user.getId())
        // .orElseThrow(() -> new RuntimeApiException("유저 정보를 찾을 수 없습니다",
        // HttpStatus.NOT_FOUND));
        Shop shopPS = shopRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeApiException("잘못된 가게 요청입니다", HttpStatus.NOT_FOUND));
        args2 = shopPS;

    }

    // cut() 메서드가 실행 되는 지점 이전에 before() 메서드 실행
    // @Before("cut()")
    // public void before(JoinPoint joinPoint) {

    // // 실행되는 함수 이름을 가져오고 출력
    // MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    // Method method = methodSignature.getMethod();
    // System.out.println(method.getName() + "메서드 실행");

    // // 메서드에 들어가는 매개변수 배열을 읽어옴
    // Object[] args = joinPoint.getArgs();

    // // 매개변수 배열의 종류와 값을 출력
    // for (Object obj : args) {
    // System.out.println("type : " + obj.getClass().getSimpleName());
    // System.out.println("value : " + obj);
    // }
    // }

    // // cut() 메서드가 종료되는 시점에 afterReturn() 메서드 실행
    // // @AfterReturning 어노테이션의 returning 값과 afterReturn 매개변수 obj의 이름이 같아야 함
    // @AfterReturning(value = "cut()", returning = "obj")
    // public void afterReturn(JoinPoint joinPoint, Object obj) {
    // System.out.println("return obj");
    // System.out.println(obj);
    // }

}
