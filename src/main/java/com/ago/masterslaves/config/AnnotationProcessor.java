//package com.ago.masterslaves.config;
//
//import com.ago.masterslaves.bean.SwitchDataSourceAnnotation;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.config.BeanPostProcessor;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Method;
//
///**
// * @ClassName:AnnotationProcessor
// * @Describe:
// * @Data:2020/3/2315:03
// * @Author:Ago
// * @Version 1.0
// */
//public class AnnotationProcessor implements BeanPostProcessor {
//
//    @Override
//    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//        SwitchDataSourceAnnotation annotation = bean.getClass().getAnnotation(SwitchDataSourceAnnotation.class);
//        if (annotation != null) {
//            Method[] methods = annotation.annotationType().getDeclaredMethods();
//            for (Method method : methods) {
//                if(method.getName().startsWith("select")){
//                    DynamicDataSourceContextHolder.userSlavesDataSource();
//                }
//            }
//        }
//        return bean;
//    }
//
//    @Override
//    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//        return bean;
//    }
//}
