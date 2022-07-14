package com.hias.utils;

import com.hias.constant.ErrorMessageCode;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

@Component
@AllArgsConstructor
public class MessageUtils {

    private static MessageUtils instance;

    private final MessageSource messageSource;

    private static final Locale localeVN = new Locale("vi", "VN");

    @PostConstruct
    void setInstance() {
        setInstance(this);
    }

    private static void setInstance(MessageUtils instance) {
        MessageUtils.instance = instance;
    }

    public static MessageUtils get() {
        if (instance == null) {
            throw new IllegalStateException("No bean found");
        }
        return instance;
    }

    public String getMessage(String key) {
        return messageSource.getMessage(key, null, localeVN);
    }

    public String getMessage(ErrorMessageCode errorMessageCode) {
        return messageSource.getMessage(errorMessageCode.getCode(), null, localeVN);
    }

}
