package miFramework.annotations;

import java.lang.annotation.*;
@Target(value=ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface claseABM
{
String tituloVentanaABM();
}