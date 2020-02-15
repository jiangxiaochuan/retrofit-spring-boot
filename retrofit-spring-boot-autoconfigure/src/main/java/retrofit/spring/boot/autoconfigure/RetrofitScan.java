package retrofit.spring.boot.autoconfigure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: sanjin
 * @date: 2020/2/15 下午9:09
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RetrofitScan {
    String value() default "com.github.sanjin";
}
