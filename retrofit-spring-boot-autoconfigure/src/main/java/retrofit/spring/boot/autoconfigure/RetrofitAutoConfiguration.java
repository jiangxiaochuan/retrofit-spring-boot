package retrofit.spring.boot.autoconfigure;

import com.github.sanjin.annotation.RetrofitConfig;
import com.github.sanjin.spring.DefaultRetrofitSpringFactory;
import com.github.sanjin.spring.RetrofitSpringFactory;
import com.github.sanjin.spring.RetrofitSpringFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author: sanjin
 * @date: 2020/2/14 下午12:12
 */
@Configuration
@ConditionalOnClass({RetrofitSpringFactoryBean.class, DefaultRetrofitSpringFactory.class})
@Slf4j
public class RetrofitAutoConfiguration implements BeanFactoryAware, ImportBeanDefinitionRegistrar{

    private BeanFactory beanFactory;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(RetrofitScan.class.getName()));

        if (!AutoConfigurationPackages.has(this.beanFactory)) {
            log.debug("Could not determine auto-configuration package, automatic retrofitConfig scanning disabled.");
            return;
        }

        log.debug("Searching for retrofit annotated with @RetrofitConfig");
        // packages.get(0) -> 当前所在包
        List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
        if (log.isDebugEnabled()) {
            packages.forEach(pkg -> log.debug("Using auto-configuration base package '{}'", pkg));
        }

        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RetrofitScanConfigure .class);
        builder.addPropertyValue("annotationClass", RetrofitConfig.class);
        builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(packages));

        registry.registerBeanDefinition(RetrofitScanConfigure.class.getName(), builder.getBeanDefinition());
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

}
