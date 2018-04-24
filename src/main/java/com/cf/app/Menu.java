package com.cf.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

// code_snippet cookSpecialOne start java
@RefreshScope
@Component
public class Menu {

  @Value("${cook.special:Workd}")
  String special;
// code_snippet cookSpecialOne end


// code_snippet cookSpecialTwo start java
  public String getSpecial() {
    return special;
  }
// code_snippet cookSpecialTwo end

  
}
