package com.ragnarock.musicrecommends.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Music Recommendations API",
                description = "API для меломанов, позволяющее"
                        + "пользователям искать интересующую их музыку.",
                version = "1.0.0",
                contact = @Contact(
                        name = "dstpia",
                        email = "ilya14022006@gmail.com",
                        url = "https://github.com/dstpia"
                )
        )
)
public class SwaggerConfig {
}