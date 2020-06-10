
package com.dsapimx.login.back;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    private String name;
    private String birthDate;
    private String sex;
    private String email;
    private String phone;
    private String password;
    
    public boolean ValidaDatos(String name, String birthDate, String sex, String email, String phone, String password){
        
        boolean valida_name=true;
        boolean valida_birthDate=false;
        boolean valida_sex=true;
        boolean valida_email=false;
        boolean valida_phone=false;
        boolean valida_password=false;
        
        
        if(name.isEmpty()||birthDate.isEmpty()||sex.isEmpty()||email.isEmpty()||phone.isEmpty()||password.isEmpty()){
            
            return false;
            
        }else{
            
            //Valida birthDate--------------------------------------------------
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaNac = LocalDate.parse(birthDate, fmt);
            LocalDate ahora = LocalDate.now();

            Period periodo = Period.between(fechaNac, ahora);
            
            if(periodo.getYears() > 18){
                valida_birthDate = true;
            }else{
                valida_birthDate = false;
            }
            //------------------------------------------------------------------
                
            //Verifica formado de email-----------------------------------------
            Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
        
            Matcher matcher = pattern.matcher(email);

            if (matcher.matches()) {
                valida_email = true;
            }else {
                valida_email = false;
            }
            //------------------------------------------------------------------
            
            //Valida phone------------------------------------------------------
            
            Pattern patron_phone = Pattern.compile("[^0]+\\d{8}");
            Matcher verifica_phone = patron_phone.matcher(phone);
            
            if(verifica_phone.matches()){
                valida_phone = true;
            }else{
                valida_phone = false;
             }
            
            //------------------------------------------------------------------
            
            //Valida password------------------------------------------------------
            
            
            
            Pattern patron_password = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
            Matcher verifica_password = patron_password.matcher(password);
            
            if(verifica_password.matches()){
                valida_password = true;
            }else{
                valida_password = false;
             }
            //------------------------------------------------------------------
            if(!valida_name || !valida_birthDate || !valida_sex || !valida_email || !valida_phone || !valida_password){
                return false;
            }else{
                return true;
            }    
        }
                
        
        
        
    }
}