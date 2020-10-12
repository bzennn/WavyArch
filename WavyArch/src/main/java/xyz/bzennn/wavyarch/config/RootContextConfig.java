package xyz.bzennn.wavyarch.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.context.annotation.ComponentScan.Filter;

@Configuration
@ComponentScan(basePackages = { "xyz.bzennn.wavyarch" }, excludeFilters = {
		@Filter(type = FilterType.ANNOTATION, classes = EnableWebMvc.class)//,
		//@Filter(org.springframework.stereotype.Controller.class)
	})
public class RootContextConfig {

}
