package demo.flowable.support.conf;

import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * 可以使用SpringProcessEngineConfiguration中的额外参数+deploymentMode+，定制部署的方式。这个参数定义了对于一组符合过滤器的资源，组织部署的方式。默认这个参数有3个可用值：
 * <p>
 * default: 将所有资源组织在一个部署中，整体用于重复检测过滤。这是默认值，在未设置这个参数时也会用这个值。
 * single-resource: 为每个资源创建一个单独的部署，并用于重复检测过滤。如果希望单独部署每一个流程定义，并且只有在它发生变化时才创建新的流程定义版本，就应该使用这个值。
 * resource-parent-folder: 为同一个目录下的资源创建一个单独的部署，并用于重复检测过滤。这个参数值可以为大多数资源创建独立的部署。同时仍可以通过将部分资源放在同一个目录下，将它们组织在一起。
 */
@Configuration
public class CustomProcessEngineConfiguration implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {
        // deploymentMode
        engineConfiguration.setDeploymentMode("single-resource");
        // 流程图
        engineConfiguration.setActivityFontName("宋体");
        engineConfiguration.setLabelFontName("宋体");
        engineConfiguration.setAnnotationFontName("宋体");
    }

}
