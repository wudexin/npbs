package com.nantian.npbs.common.utils.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class LocalI18NMessageSource {
	MessageSource messageSource;
	
	private static LocalI18NMessageSource instance;
	private LocalI18NMessageSource() {
		//instance = this;
	}
	
	public static LocalI18NMessageSource getInstance(){
		if(instance == null){
			instance = new LocalI18NMessageSource();
		}
        return instance;
    }
	
	public String getMessage(String messageKey){
		return messageSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
	}
	public MessageSource getMessageSource() {
		return messageSource;
	}
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	
}
