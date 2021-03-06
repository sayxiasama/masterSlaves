package com.ago.masterslaves.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SwitchDataSourceAnnotation {

    String methodName() default "";
}
