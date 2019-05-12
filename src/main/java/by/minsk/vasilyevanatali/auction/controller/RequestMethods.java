package by.minsk.vasilyevanatali.auction.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code RequestMethods} is a helper annotation for request method classifying.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@interface RequestMethods {

    String[] gets();

    String[] posts();
}
