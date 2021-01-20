import com.yu.mapper.userMapperImpl;
import com.yu.pojo.book;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

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
