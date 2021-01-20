##Spring AOP 切面
导入aop需要的jar包
```xml
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.6</version>
</dependency>
```
首先得导入APO的XML的命名空间
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        https://www.springframework.org/schema/aop/spring-aop.xsd">
```
###第一种通过实现spring的接口的方法实现代理
在方法在线前切入实现spring的接口：MethodBeforeAdvice 重写接口的before方法<br>
例子：
```java
public class BeforLog implements MethodBeforeAdvice {
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
```
在方法执行后切入实现spring的AfterReturningAdvice接口:重写接口的afterReturning方法<br>
也可以实现AfterAdvice接口<br>
例子：
```java
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
```
装配xml让方法生效就得配置AOP的config
```xml
    <bean id="befor" class="com.yu.log.BeforLog"/>
    <bean id="alter" class="com.yu.log.AfterLog"/>
    <bean id="service" class="com.yu.service.userApiImpl"/>
        <aop:config>
          <!--  配置切入点  execution表达式：(* com.yu.service.userApiImpl.*(..)) -->
            <aop:pointcut id="pointcut" expression="execution(* com.yu.service.userApiImpl.*(..))"/>
            <aop:advisor advice-ref="alter" pointcut-ref="pointcut"/>
            <aop:advisor advice-ref="befor" pointcut-ref="pointcut"/>
        </aop:config>
```
execution表达式意思：<br>
            *  代表返回值修饰类型  *代表通配符任意<br>
            
            com.yu.service.userApiImpl 类的位置<br>
            * 代表方法 可以是方法名 * 通配符 任意方法<br>
            (..) 代表参数 可以指定参数的类型和个数 ..通配符 可变参数可以有多个<br>
详情可百度execution表达式，或打开这个网站：https://www.cnblogs.com/gdwkong/p/8660027.html<br>
测试:
z```java
ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        userAPI service =(userAPI) context.getBean("service");
        service.query();
```    
动态代理代理的是接口所以得转为接口类型在掉方法
###第二种自定义类来实现代理
自己写一个类在类中定义代理增强的方法
```java
public class diyPointcut {
    public void before(){
        System.out.println("----------------方法执行前--------------");
    }
    public void after(){
        System.out.println("---------------方法执行后--------------");
    }
}
```
在xml配置AOP时使用切面
```xml
 <bean id="service" class="com.yu.service.userApiImpl"/>
 <bean id="diy" class="com.yu.diy.diyPointcut"/>     
        <!-- 第二种自定义切面实现代理 -->
    <aop:config>
        <!-- ref引用的是自定义类 -->
        <aop:aspect ref="diy">
            <!--切入点-->
            <aop:pointcut id="pointcut" expression="execution(* com.yu.service.userApiImpl.*(..))"/>
            <!--通知-->
            <!-- 在方法执行前切入 -->
            <aop:before method="before" pointcut-ref="pointcut"/>
            <!-- 在方法执行后切入 -->
            <aop:after method="after" pointcut-ref="pointcut"/>
        </aop:aspect>
    </aop:config>
```  
测试:
```java
public class test {
    @Test
    public void Test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        userAPI service =(userAPI) context.getBean("service");
        service.query();
    }
}

```   
###第三种注解方式实现AOP
 编写类实现动态代理 在类上添加@Aspect注解标记这是一个切面<br>
 方法中加入注解注解的参数就是一个切入点<br>
 例子：
 ```java
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect//标记这是一个切面
public class DiyAOP {
    @Before("execution(* com.yu.service.userApiImpl.*(..))") //参数是切入点的execution表达式
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
``` 
在xml中将这个类注册进beans中 在开启注解
```xml
 <bean id="diyAOP" class="com.yu.diy.DiyAOP"/>
    <!-- 开启注解支持 expose-proxy="true"可以不写 如果不写就是默认为false为JDK的代理方式 true为cglib的代理方式 -->
    <aop:aspectj-autoproxy expose-proxy="true"/>
```