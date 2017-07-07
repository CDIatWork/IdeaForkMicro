package at.irian.cdiatwork.ideafork.config.client.internal.context;

import javax.enterprise.context.NormalScope;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@NormalScope
@Target({TYPE, METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ConfigScoped {
}
