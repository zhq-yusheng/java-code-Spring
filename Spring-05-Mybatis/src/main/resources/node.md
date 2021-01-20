##本章节是spring整合mybatis
要导入的maven依赖包
```xml
<dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.6</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.21</version>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.0.14</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.6</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.3.1</version>
        </dependency>
```
在spring中对象的创建是有beans.xml的配置文件来做的，所以在spring中的那个工具类就不能在这样创建了，得在beans.xml文件中创建<br>
首先得先创建一个数据源(DataSource) 这里使用的是阿里巴巴的Druid连接池
```xml
<!--导入druid外部配置文件-->
<context:property-placeholder location="classpath:jdbc.properties"/>
<!--创建数据源 这使用的是阿里的Druid数据库连接池 可以使用其他的数据源的-->
<bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
    <property name="driverClassName" value="${jdbc.driverClassName}"/>
    <property name="url" value="${jdbc.url}"/>
    <property name="username" value="${jdbc.username}"/>
    <property name="password" value="${jdbc.password}"/>
    <property name="initialSize" value="${jdbc.initialSize}"/>
    <property name="maxActive" value="${jdbc.maxActive}"/>
    <property name="maxWait" value="${jdbc.maxWait}"/>
 </bean>
```
然后在创建sqlSessionFactory对象，sqlSessionFactory对象中可以将mybatis无缝的衔接到spring中，sqlSessionFactory能配置mybatis所有的配置如；配置、起别名、衔接数据源、注册mapper等....<br>
```xml
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <!-- sqlSessionFactory必须配置的一个属性就是DataSource 这引用上面配置好的数据源 -->
    <property name="dataSource" ref="dataSource" />
    <!-- 无缝衔接mybatis的配置文件两个配置文件可以配合使用 -->
    <property name="configLocation" value="classpath:mybatis-config.xml"/>
    <!-- 注册mapper -->
    <property name="mapperLocations" value="com/yu/mapper/*.xml"/>
</bean>
```
创建执行SQL语句的sqlSession对象，在spring中创建的是SqlSessionTemplate,但是二者没有什么区别，用法一样
```xml
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
    <!-- 因为SqlSessionTemplate没有set方法就只能构造方法注入sqlSessionFactory -->
    <constructor-arg index="0" ref="sqlSessionFactory"/>
</bean>
```
这样mybatis的工具类就不用了，在spring中调用也有区别这里要把接口加个实现类<br>
例子：对应数据库的实体类
```java
public class book {
    private String bookName;
    private  String author;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "book{" +
                "bookName='" + bookName + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
```
mapper接口
```java
public interface userMapper {
    List<book> select();
}
```
mapper对应的xml文件
```xml
<?xml version="1.0" encoding="UTF8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.yu.mapper.userMapper">
   <select id="select" resultType="book">
       select * from book
   </select>
</mapper>
```
这是spring和mybatis的不同地方加接口的实现类,创建一个sqlSession属性，写上set方法好在beans.xml中注入sqlSession
```java
public class userMapperImpl implements userMapper {
    SqlSessionTemplate sqlSession;
    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<book> select() {
        return sqlSession.getMapper(userMapper.class).select();
    }
}
```
在beans.xml中注册接口的实现对象并且注入sqlSession对象
```xml
<bean id="user" class="com.yu.mapper.userMapperImpl">
    <property name="sqlSession" ref="sqlSession"/>
</bean>
```
测试
```java
public class text {
    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        userMapperImpl user = context.getBean("user", userMapperImpl.class);
        List<book> select = user.select();
        for (book book : select) {
            System.out.println(book);
        }

    }
}

```
