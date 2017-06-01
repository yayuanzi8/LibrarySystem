package librarySystem.config;

import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

public class SystemInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{RootConfig.class,WebConfig.class,SecurityConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected void customizeRegistration(ServletRegistration.Dynamic registration) {
        MultipartConfigElement element = new MultipartConfigElement("/uploads/");
        registration.setMultipartConfig(element);
        registration.setLoadOnStartup(1);
    }

    @Override
    protected void registerContextLoaderListener(ServletContext servletContext) {
        //注册会话监听器
        HttpSessionEventPublisher publisher = new HttpSessionEventPublisher();
        servletContext.addListener(publisher);
        super.registerContextLoaderListener(servletContext);
    }
}
