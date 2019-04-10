package aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class Audience {

    @Pointcut("execution(* aop.Performance.perform(String, int))")
    public void performance() {
    }

//    @Before("performance()")
//    public void silenceCellPhones() {
//        System.out.println("Silencing cell phones");
//    }
//
//    @Before("performance() && args(name, time)")
//    public void takeSeats(int time, String name) {
//        System.out.println("Taking seats..." + " and prepare watch " + name + ":" + time);
//    }
//
//    @AfterReturning("performance()")
//    public void applause() {
//        System.out.println("CLAP CLAP CLAP!!!");
//    }

    @AfterThrowing("performance()")
    public void demandRefund() {
        System.out.println("Demanding a refund");
    }

    @Around("performance() && args(name, time)")
    public void watchPerformance(ProceedingJoinPoint jp, String name, int time) {
        try {
            System.out.println("Silencing cell phones");
            System.out.println("Taking seats...");
            jp.proceed(new Object[]{"(" + name + ")", time * 10});
            System.out.println("CLAP CLAP CLAP!!!");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
