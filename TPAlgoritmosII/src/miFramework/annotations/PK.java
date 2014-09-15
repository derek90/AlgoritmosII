package miFramework.annotations;

import java.lang.annotation.*;
@Target(value=ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PK
{
boolean autoincrement() default false;
}