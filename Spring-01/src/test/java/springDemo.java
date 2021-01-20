import UserDao.UserDaoimpl;
import UserDao.userT;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class springDemo {
    @Test
    public void Spring(){
        ApplicationContext context = new ClassPathXmlApplicationContext("bens.xml");
        userT user =(userT) context.getBean("userT");
        System.out.println(user);
    }
}
