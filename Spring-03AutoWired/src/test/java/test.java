import com.yu.config.BeanConfig;
import com.yu.config.student;
import com.yu.dao.People;
import com.yu.pojo.user;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class test {
    @Test
    public void test(){
      ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        People people = context.getBean("people", People.class);
        people.getDog().method();
        System.out.println(people.getDog().getName());
        people.getCat().Method();
        System.out.println(people.getCat().getName());
    }
    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        user user = context.getBean("user", user.class);
        System.out.println(user.getName());
    }
    @Test
    public void test2(){
        ApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);
        student student = context.getBean("getStudent", student.class);
        System.out.println(student.getName());
    }
}
