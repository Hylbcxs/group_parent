package usts.cs2020;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("usts.cs2020")
public class ActivityApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(ActivityApplication.class,args);
    }
}
