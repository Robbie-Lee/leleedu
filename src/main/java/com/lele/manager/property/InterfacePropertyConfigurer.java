package com.lele.manager.property;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class InterfacePropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Properties ctxProperties;  
    
    @Override  
    protected void processProperties(ConfigurableListableBeanFactory beanFactory,  
            Properties props) throws BeansException {  
  
        super.processProperties(beanFactory, props); 
        ctxProperties = props;
    }

    public static Properties getCtxProperties() {
    	return ctxProperties;
    }
}
