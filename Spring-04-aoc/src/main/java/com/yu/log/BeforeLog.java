package com.yu.log;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * 在方法执行前切入 实现MethodBeforeAdvice 重写before方法进行操作
 */

public class BeforeLog implements MethodBeforeAdvice {
    /**
     * @param method 执行的方法
     * @param objects  args 方法的参数
     * @param o target 执行的对象
     * @throws Throwable
     */
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("执行的对象是："+o.getClass().getName()+" 执行的方法是："+method.getName());
    }
}
