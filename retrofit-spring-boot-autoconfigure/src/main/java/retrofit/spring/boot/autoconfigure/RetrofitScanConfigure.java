package retrofit.spring.boot.autoconfigure;

import com.github.sanjin.annotation.RetrofitConfig;
import com.github.sanjin.spring.RetrofitSpringFactory;
import lombok.Data;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyResourceConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Optional;

/**
 * @author: sanjin
 * @date: 2020/2/14 下午1:37
 */
@Data
public class RetrofitScanConfigure implements BeanDefinitionRegistryPostProcessor,ApplicationContextAware {
    private String basePackage;
    private Class<? extends Annotation> annotationClass;

    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //processPropertyPlaceHolders();
        ClassPathRetrofitConfigScanner scanner = new ClassPathRetrofitConfigScanner(registry);
        String[] scanPackages = StringUtils.tokenizeToStringArray(
                // 多个package,以","分割,如 com.xxx,com.yyy
                this.basePackage,
                ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        scanner.registerFilter();
        scanner.scan(scanPackages);
    }

    private void processPropertyPlaceHolders() {
        String beanName = "mapperScanner";
        Map<String, PropertyResourceConfigurer> prcs = applicationContext.getBeansOfType(PropertyResourceConfigurer.class);

        if (!prcs.isEmpty() && applicationContext instanceof ConfigurableApplicationContext) {
            BeanDefinition mapperScannerBean = ((ConfigurableApplicationContext) applicationContext).getBeanFactory()
                    .getBeanDefinition(beanName);

            // PropertyResourceConfigurer does not expose any methods to explicitly perform
            // property placeholder substitution. Instead, create a BeanFactory that just
            // contains this mapper scanner and post process the factory.
            DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
            factory.registerBeanDefinition(beanName, mapperScannerBean);

            for (PropertyResourceConfigurer prc : prcs.values()) {
                prc.postProcessBeanFactory(factory);
            }

            PropertyValues values = mapperScannerBean.getPropertyValues();

        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // pass
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
