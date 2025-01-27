package sideproject.urkunde.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final Contact DEFAULT_CONTACT = new Contact("이수찬", "http://www.urkunde.com", "tncksdl05@gmail.com");

    private static final ApiInfo DEFAULT_API_INFO = new ApiInfo("REST API", "My REST API", "1.0",
            "urn:tos", DEFAULT_CONTACT.toString(), "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0");

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES = new HashSet<>(Arrays.asList("application/json"));

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                .consumes(DEFAULT_PRODUCES_AND_CONSUMES)
                .apiInfo(DEFAULT_API_INFO).select()
                .apis(RequestHandlerSelectors.basePackage("sideproject.urkunde.controller"))
                .paths(PathSelectors.any())
                .build();
    }




}
