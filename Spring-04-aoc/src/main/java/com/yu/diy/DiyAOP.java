package com.yu.diy;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;


@Aspect
public class DiyAOP {
    @Before("execution(* com.yu.service.userApiImpl.*(..))")
    public void before(){
        System.out.println("-----执行前");
    }
    @After("execution(* com.yu.service.userApiImpl.*(..))")
    public void alter(){
        System.out.println("-----方法执行后");
    }
    //了解
    @Around("execution(* com.yu.service.userApiImpl.*(..))") //环绕增强
    public void around(ProceedingJoinPoint pt) throws Throwable {
        System.out.println("执行前");
        Object proceed = pt.proceed();//执行方法相当于动态代理中的invoke方法 返回的对象什么都不是是个null
        System.out.println("执行后");
        Signature signature = pt.getSignature(); //或得签名 相当tostring方法获取类的全部信息
        System.out.println(signature);
}
}
