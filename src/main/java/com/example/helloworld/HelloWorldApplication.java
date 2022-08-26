package com.example.helloworld;

import com.example.helloworld.resources.PeopleResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Environment;
import com.example.helloworld.resources.HelloWorldResource;
import com.example.helloworld.health.TemplateHealthCheck;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.db.DataSourceFactory;
import com.example.helloworld.db.PersonDAO;
import com.example.helloworld.core.Person;


public class HelloWorldApplication extends Application<HelloWorldConfiguration> {

    public static void main(final String[] args) throws Exception {
        System.out.println("###1");
        new HelloWorldApplication().run(args);
    }

    @Override
    public String getName() {
        return "HelloWorld";
    }

    private final HibernateBundle<HelloWorldConfiguration> hibernate = new HibernateBundle<HelloWorldConfiguration>(Person.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(HelloWorldConfiguration configuration) {
            System.out.println("####3");
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(final Bootstrap<HelloWorldConfiguration> bootstrap) {
        // TODO: application initialization
        System.out.println("###2");
        bootstrap.addBundle(new AssetsBundle("/web/assets/", "/", "index.html"));
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(final HelloWorldConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
        System.out.println("####4");
        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()

        );

        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);

        final PersonDAO dao = new PersonDAO(hibernate.getSessionFactory());
        environment.jersey().register(dao);
        final PeopleResource peopleResource = new PeopleResource(dao);
        environment.jersey().register(peopleResource);

    }


}
