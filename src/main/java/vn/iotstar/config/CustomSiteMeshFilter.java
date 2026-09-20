package vn.iotstar.config;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class CustomSiteMeshFilter extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        // decorator mặc định
        builder.addDecoratorPath("/*", "/decorators/web.jsp")
                // decorator riêng theo đường dẫn
                .addDecoratorPath("/admin/*", "/decorators/admin.jsp")
                // các đường dẫn không decorate
                .addExcludedPath("/login*").addExcludedPath("/login/*")
                .addExcludedPath("/alogin*").addExcludedPath("/alogin/*")
                .addExcludedPath("/api/**")
                // Swagger
                .addExcludedPath("/swagger-ui/*").addExcludedPath("/swagger-ui*")
                .addExcludedPath("/swagger-ui**")
                .addExcludedPath("/v3/api-docs*").addExcludedPath("/v3/api-docs/*")
                // GraphQL (dùng cho mục 5)
                .addExcludedPath("/apis/**")
                .addExcludedPath("/graphiql*").addExcludedPath("/graphiql/*");
    }
}