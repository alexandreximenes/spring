package com.springboot.update.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class I18nMessageResponse {

    @Autowired
    private MessageSource messageSource;

    public String getGoodMorning(String ... args) {
        return resolveMessageSource("good.morning", args);
    }

    public String getGoodNight(String ... args) {
        return resolveMessageSource("good.night", args);
    }

    public String resolveMessageSource(String message, String ... args){
        return messageSource.getMessage(message, args, LocaleContextHolder.getLocale());
    }
}
