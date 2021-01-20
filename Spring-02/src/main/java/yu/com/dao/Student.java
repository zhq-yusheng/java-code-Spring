package yu.com.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Student {
    private String name;
    private Map<String,String> information;
    private String[] hobby;
    private String wife;
    private List<String> LoveBook;
    private Properties pro;
    private Address address;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getInformation() {
        return information;
    }

    public void setInformation(Map<String, String> information) {
        this.information = information;
    }

    public String[] getHobby() {
        return hobby;
    }

    public void setHobby(String[] hobby) {
        this.hobby = hobby;
    }

    public String getWife() {
        return wife;
    }

    public void setWife(String wife) {
        this.wife = wife;
    }

    public List<String> getLoveBook() {
        return LoveBook;
    }

    public void setLoveBook(List<String> loveBook) {
        LoveBook = loveBook;
    }

    public Properties getPro() {
        return pro;
    }

    public void setPro(Properties pro) {
        this.pro = pro;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", information=" + information +
                ", hobby=" + Arrays.toString(hobby) +
                ", wife='" + wife + '\'' +
                ", LoveBook=" + LoveBook +
                ", pro=" + pro +
                ", address=" + address +
                '}';
    }
}
