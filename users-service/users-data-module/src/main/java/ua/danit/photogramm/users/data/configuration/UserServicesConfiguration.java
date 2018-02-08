package ua.danit.photogramm.users.data.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * String configuration for user data services in the system.
 * Point of migration to another modules.
 *
 * @author Andrey Minov
 */
@Configuration
@ComponentScan("ua.danit.photogramm.users.data.services")
public class UserServicesConfiguration {
}
