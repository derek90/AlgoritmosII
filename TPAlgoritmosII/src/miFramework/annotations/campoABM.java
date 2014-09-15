package miFramework.annotations;

import java.lang.annotation.*;
@Target(value=ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface campoABM
{
String nombreParaABM(); 
boolean required() default true;
int maxLength() default 30;
}