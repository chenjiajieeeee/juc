import java.util.Date;

public class Concurrent {
    public static void main(String[] args) {
        Test test=new Test(new Date());
        test.date1.setTime(1321);
        System.out.println(test.date1);

        test.date1.setTime(321321);
        System.out.println(test.date1);

    }
}
