package retrofit.spring.boot.autoconfigure;

import com.github.sanjin.annotation.RetrofitConfig;
import com.github.sanjin.spring.DefaultRetrofitSpringFactory;
import com.github.sanjin.spring.RetrofitSpringFactory;
import com.github.sanjin.spring.RetrofitSpringFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

/**
 * @author: sanjin
 * @date: 2020/2/15 下午7:36
 */
@Slf4j
public class ClassPathRetrofitConfigScanner extends ClassPathBeanDefinitionScanner {
    private Class<? extends Annotation> retrofitAnnotation;
    private Class<? extends FactoryBean> retrofitFactoryBeanClass = RetrofitSpringFactoryBean.class;

    public ClassPathRetrofitConfigScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    public void registerFilter() {
        addIncludeFilter(new AnnotationTypeFilter(RetrofitConfig.class));
    }

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            log.warn("No RetrofitConfig was found in '" + Arrays.toString(basePackages)
                    + "' package. Please check your configuration.");
        } else {
            processBeanDefinitionHolders(beanDefinitions);
        }

        return beanDefinitions;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().hasAnnotation(RetrofitConfig.class.getName());
    }

    // 给BeanDefinition添加FactoryBean,最终实例化代理类
    private void processBeanDefinitionHolders(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        RetrofitSpringFactory retrofitSpringFactory = new DefaultRetrofitSpringFactory();
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitionHolders) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            log.debug("Creating RetrofitFactoryBean with name '" + holder.getBeanName() + "' and '" + beanClassName
                    + "' retrofitInterface");

            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName); // issue #59
            definition.getConstructorArgumentValues().addGenericArgumentValue(retrofitSpringFactory); // issue #59
            definition.setBeanClass(this.retrofitFactoryBeanClass);
            definition.setLazyInit(true);
        }
    }
}
