package com.smartvoucher.webEcommercesmartvoucher.util;

import org.springframework.stereotype.Component;

@Component
public class StringsUtil {
    public String getUserNameFormDomain(String domain){
        int index = domain.indexOf("@");
        return domain.substring(0, index);
    }
}
