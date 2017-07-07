package at.irian.cdiatwork.ideafork.notification.integration.spring;

import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.apache.deltaspike.core.util.ProxyUtils;
import org.apache.deltaspike.core.util.bean.BeanBuilder;
import org.apache.deltaspike.core.util.metadata.builder.AnnotatedTypeBuilder;
import org.apache.deltaspike.core.util.metadata.builder.ContextualLifecycle;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.enterprise.context.Dependent;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.*;
import java.util.Collections;

public class SpringBridgeExtension implements Extension {
    private ConfigurableApplicationContext springContext;

    public void initContainerBridge(@Observes AfterBeanDiscovery abd, BeanManager beanManager) {
        springContext = bootContainer();

        for (String beanName : springContext.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = springContext.getBeanFactory().getBeanDefinition(beanName);
            Class<?> beanClass = springContext.getType(beanName);

            if (!beanClass.getName().startsWith("org.springframework.")) {
                abd.addBean(createBeanAdapter(beanClass, beanName, beanDefinition, springContext, beanManager));
            }
        }
    }

    public void cleanup(@Observes BeforeShutdown beforeShutdown) {
        springContext.close();
    }

    private <T> Bean<T> createBeanAdapter(Class<T> beanClass, String beanName, BeanDefinition beanDefinition,
                                          ConfigurableApplicationContext applicationContext, BeanManager bm) {
        String beanScope = beanDefinition.getScope();
        ContextualLifecycle lifecycleAdapter = new SpringAwareBeanLifecycle(applicationContext, beanName, beanScope);

        beanClass = ProxyUtils.getUnproxiedClass(beanClass);
        //we don't need to handle (remove) interceptor annotations, because BeanBuilder >won't< add them (not supported)
        return new BeanBuilder<T>(bm)
                .readFromType(new AnnotatedTypeBuilder<T>().readFromType(beanClass).create())
                .name(beanName)
                .beanLifecycle(lifecycleAdapter)
                .injectionPoints(Collections.<InjectionPoint>emptySet())
                .scope(Dependent.class) //the instance (or proxy) returned by spring shouldn't get proxied
                .create();
    }

    private ConfigurableApplicationContext bootContainer() {
        String contextXml = ConfigResolver.getPropertyValue("springContextXml", "/META-INF/applicationContext.xml");
        ConfigurableApplicationContext configurableApplicationContext = new ClassPathXmlApplicationContext(new String[]{contextXml}, false);

        configurableApplicationContext.refresh();
        return configurableApplicationContext;
    }
}
