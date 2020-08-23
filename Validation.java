package com.dsapimx.login.back.bussines.impl.core;

import com.dsapimx.login.back.exception.ValidationException;
import com.dsapimx.login.back.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Validation {

    public void validateUser( User user ) throws ValidationException {
        if ( user.getName() == null || user.getName().isEmpty() ) {
            throw new ValidationException( "El nombre es invalido" );
        }
        if ( user.getEmail() == null || user.getEmail().isEmpty() || isValidEmail( user.getEmail() )) {
            throw new ValidationException( "El Email es invalido" );
        }
        if ( user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty() || user.getPhoneNumber().length() < 10 || !charSetIsNumeric( user.getPhoneNumber() ) ) {
            throw new ValidationException( "El numero telefonico es invalido" );
        }
        if ( user.getBirthDate() == null || user.getBirthDate().isEmpty()  ) {
            throw new ValidationException( "La fecha de nacimiento es invalida" );
        }
        if ( !isValidAge( user.getBirthDate() ) ) {
            throw new ValidationException( "Debes ser mayor a 18 años" );
        }
        if ( user.getSex() == null || user.getSex().isEmpty() ) {
            throw new ValidationException( "El Sexo es invalido" );
        }
        if ( user.getPassword() == null || user.getPassword().isEmpty() || !isValidPassword( user.getPassword() ) ) {
            throw new ValidationException( "La contraseña es invalida" );
        }
    }

    private boolean isValidAge( String sBirthDate ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd" );
        LocalDate birthDate = LocalDate.parse( sBirthDate, formatter );
        LocalDate now = LocalDate.now();
        Period period = Period.between( birthDate, now );
        return period.getYears() > 18;
    }

    private boolean isValidPassword( String value ) {
        final String regex = "\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])\\S{8,}\\z";
        return value.matches( regex );
    }

    private boolean isValidEmail( String value ) {
        final String regex = "([a-z0-9]+(\\\\.?[a-z0-9])*)+@(([a-z]+)\\\\.([a-z]+))+";
        return value.matches( regex );
    }

    private boolean charSetIsNumeric( String val ) {
        final String regex = "[+-]?[0-9][0-9]*";
        Pattern p = Pattern.compile( regex );
        Matcher m = p.matcher( val );
        return m.find() && m.group().equals( val );
    }

    public String duplicateUniqueKey( String errorMessage ) {
        if ( errorMessage.contains( "UK_EMAIL" ) ) {
            return "Ya existe un registro con ese correo";
        } else if ( errorMessage.contains( "UK_PHONE" ) ) {
            return "Ya existe un registro con ese numero";
        } else {
            return errorMessage;
        }
    }
}
