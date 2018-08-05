package com.vfp.tres;

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class ManageLanguages {

 private String locale = "en";

 public String getLocale() {
 return locale;
 }

 public void setLocale(String locale) {
 this.locale = locale;
 }

 public String changeLanguage(String locale) {
 this.locale = locale;
 FacesContext.getCurrentInstance().getViewRoot()
 .setLocale(new Locale(this.locale));
 return locale;
 }

}