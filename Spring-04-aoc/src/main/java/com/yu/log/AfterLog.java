package com.yu.log;

import org.springframework.aop.AfterReturningAdvice;
import java.lang.reflect.Method;

/**
 * 在方法执行后切入 实现AfterReturningAdvice是最终的带有返回结果的方法
 *                 也可以实现AfterAdvice方法
 */

public class AfterLog implements AfterReturningAdvice {
    /**
     *
     * @param o return 返回值
     * @param method 执行的方法
     * @param objects args 方法参数
     * @param o1 target 目标对象 执行的对象
     * @throws Throwable
     */
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("执行"+method.getName()+"后返回的结果是："+o);
    }
}
