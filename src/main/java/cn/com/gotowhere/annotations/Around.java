package cn.com.gotowhere.annotations;

import java.lang.annotation.*;

/**
 * 自定义方法注解 此时用于定时器注解便于生成方法以及方法的作用
 * (获取定时器任务的方法以及名称)
 */
@Documented
@Target({ElementType.METHOD}) //注解应用类型(应用到方法的注解,还有类的可以自己试试)
@Retention(RetentionPolicy.RUNTIME) // 注解的类型
public @interface Around {
    //属性字段名称 默认空字符串
    String name() default "";
}
