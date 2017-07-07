package at.irian.cdiatwork.ideafork.notification.integration.spring;

import org.apache.deltaspike.core.util.metadata.builder.ContextualLifecycle;
import org.springframework.context.ConfigurableApplicationContext;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;

class SpringAwareBeanLifecycle implements ContextualLifecycle {
    private final ConfigurableApplicationContext applicationContext;
    private final String beanName; //unique spring-bean-id
    private final boolean prototypeScope;

    public SpringAwareBeanLifecycle(ConfigurableApplicationContext applicationContext, String beanName, String scope) {
        this.applicationContext = applicationContext;
        this.beanName = beanName;
        this.prototypeScope = "prototype".equalsIgnoreCase(scope);
    }

    @Override
    public Object create(Bean bean, CreationalContext creationalContext) {
        return this.applicationContext.getBean(this.beanName);
    }

    @Override
    public void destroy(Bean bean, Object instance, CreationalContext creationalContext) {
        if (this.prototypeScope) {
            this.applicationContext.getBeanFactory().destroyBean(this.beanName, instance);
        }
    }
}
