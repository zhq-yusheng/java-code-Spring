#本小节是自动装配
##bean的作用域
spring的bean的有：<br>singleton:单例模式 保证全局的对象只有一个 spring默认的就是单列
<br>prototype：原型 每一次创建的对象都是一个新的对象
<br>request : 请求 数据在请求中
<br>session：会话
例子：
```xml
<bean id="people" class="com.yu.dao.People" autowire="byName" scope="prototype">
        <property name="name" value="张三"/>
    </bean>
```
##xml进行自动装配
要自动装配的bean中添加autowire属性属性的两个值：byName byType<br>
byName：按照bean的名字找 如果多个bean的id相同就会找不到报错(名字得唯一)<br>
byType：按照类型找 如果创建的多个相同的类型就也会报错(类型得唯一)
<br>
例子
```xml
<bean id="dog" class="com.yu.dao.Dog">
        <property name="name" value="小狗..."/>
    </bean>
    <bean id="cat" class="com.yu.dao.cat">
        <property name="name" value="小猫"/>
    </bean>
<bean id="people" class="com.yu.dao.People" autowire="byName">
        <property name="name" value="张三"/>
</bean>
```
##注解自动装配装配
首先我们得导入xml依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>
```
<context:annotation-config/> 的意思是开启注解，如果不用这个注解无效<br>
@Autowired 注解自动装配 通过byType进行匹配<br>
@Qualifier("xxx") 如果有多个时就可以用这个注解来指定装配那个参数就是id名<br>
@Resource 此注解比@Autowired注解高级，他会先byName进行匹配没匹配上在byType匹配，如果有多个类型一样的可以在注解中加上name属性<br>
例子：
```java
    @Autowired
    @Qualifier("cat1")
    private cat cat;
    @Resource(name = "dog1")
    private Dog dog;
    private String name;
```
##注解 
spring注解查看网址：https://blog.csdn.net/baidu_39322753/article/details/101013192<br>
@NonNull 可以为空<br>
@Autowired 注解自动装配(使用的byType匹配装配)<br>
@Qualifier("xxx") 配合@Autowired注解使用 指定哪个类进行装配<br>
要使用以下注解得在配置文件中扫描路径下类，才能生效<br>
```xml
<context:component-scan base-package="包路径"/>
```
以下注解是代替xml的配置文件在类或者方法上添加注解就能托管与spring<br>
@Component 在类上添加就能交于spring托管相当于xml配置文件中的bean标签中的创建类类名小写就是bean的id属性<br>
@Value("xxx")  在属性上或者set方法上使用就能给属性赋值 相当于xml配置文件中的bean标签中给文件赋值<br>
@Scope("xxx")  在类上就能设置类的模式如：单例模式 原型<br>
例子
```java
@Component
@Scope("prototype")
public class user {
    @Value("张三")
    private String name;
    @Value("李四")
    public void setName(String name) {
        this.name = name;
    }
}
```
@Component的衍生注解 对应的是各种层的注解 但是和@Component的作用是一样的都是一个组件<br>
@Component : 组件,没有明确的角色<br>
@Service : 在业务逻辑层(service层)使用<br>
@Repository : 在数据访问层(dao层)使用<br>
@Controller : 在展现层(MVC--SpringMVC)使tuo用<br>
##脱离xml配置java纯配置
完全脱离xml配置就是全注解开发<br>
要先创建一个配置类加上已下注解就能实现脱离xml配置<br>
@Configuration : 声明当前类是个配置类,相当于一个Spring配置的xml文件.<br>
@ComponentScan (包路径): 自动扫描包名下所有使用 @Component @Service  @Repository @Controller 的类,并注册为Bean<br>
@WiselyConfiguration : 组合注解 可以替代 @Configuration和@ComponentScan<br>
@Bean : 注解在方法上,声明当前方法的返回值为一个Bean.方法的名字就相当于bean的ID值，取bean的时候就用方法名取<br>
@import(配置类的class反射对象) :导入配置类<br>
例子:
```java
@Configuration
@ComponentScan("com.yu.config")//扫描com.yu.config包下的所有类
public class BeanConfig {
    @Bean//注册bean
    public student getStudent(){
        return new student();
    }
}
```
测试 因为是纯java配置的得创建AnnotationConfigApplicationContext对象
```java
@Test
public void test2(){
    ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
    student student = context.getBean("getStudent", student.class);
    System.out.println(student.getName());
}
```

