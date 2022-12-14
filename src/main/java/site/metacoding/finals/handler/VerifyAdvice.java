package site.metacoding.finals.handler;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class VerifyAdvice {

    @Pointcut("@annotation(site.metacoding.finals.config.annotation.VerifyCustomer)")
    public void verifyAop() {

    }

    @Around("verifyAop()")
    public Object testAOPLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("AOP 실행됨");

        return proceedingJoinPoint.proceed();
    }

    // cut() 메서드가 실행 되는 지점 이전에 before() 메서드 실행
    @Before("cut()")
    public void before(JoinPoint joinPoint) {

        // 실행되는 함수 이름을 가져오고 출력
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println(method.getName() + "메서드 실행");

        // 메서드에 들어가는 매개변수 배열을 읽어옴
        Object[] args = joinPoint.getArgs();

        // 매개변수 배열의 종류와 값을 출력
        for (Object obj : args) {
            System.out.println("type : " + obj.getClass().getSimpleName());
            System.out.println("value : " + obj);
        }
    }

    // cut() 메서드가 종료되는 시점에 afterReturn() 메서드 실행
    // @AfterReturning 어노테이션의 returning 값과 afterReturn 매개변수 obj의 이름이 같아야 함
    @AfterReturning(value = "cut()", returning = "obj")
    public void afterReturn(JoinPoint joinPoint, Object obj) {
        System.out.println("return obj");
        System.out.println(obj);
    }

}
