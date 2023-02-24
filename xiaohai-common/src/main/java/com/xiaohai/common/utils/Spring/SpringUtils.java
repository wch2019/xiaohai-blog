package com.xiaohai.common.utils.Spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring工具类 方便在非spring管理环境中获取bean
 *
 * @param
 * @author wangchenghai
 * @date 2022/08/15 17:39
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {

    /** Spring应用上下文环境 */
     private static ConfigurableListableBeanFactory beanFactory;

    private static ApplicationContext applicationContext;


    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        SpringUtils.beanFactory = beanFactory;
    }

    /**
     * Spring在bean初始化后会判断是不是ApplicationContextAware的子类
     * 如果该类是,setApplicationContext()方法,会将容器中ApplicationContext作为参数传入进去
     *
     * @param applicationContext
     * @author wangchenghai
     * @date 2022/04/01 11:46
     */

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext =  applicationContext;
    }

    /**
     * 通过Name返回指定的Bean
     *
     * @param beanClass
     * @return T
     * @author wangchenghai
     * @date 2022/04/01 11:47
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> beanClass) {
        return (T) beanFactory.getBean(beanClass);
    }

    /**
     * 获取当前的环境配置，无配置返回null
     *
     * @return 当前的环境配置
     */
    public static String[] getActiveProfiles()
    {
        return applicationContext.getEnvironment().getActiveProfiles();
    }
}
