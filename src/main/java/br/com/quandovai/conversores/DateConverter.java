package br.com.quandovai.conversores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.enterprise.context.ApplicationScoped;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.converter.Converter;

@Convert(value = Date.class)
@ApplicationScoped
public class DateConverter implements Converter<Date> {

    private String formato = "dd/MM/yyyy";

    @Override
    public Date convert(String value, Class<? extends Date> type) {
	try {
	    return new SimpleDateFormat(formato).parse(value);
	} catch (ParseException e) {
	    e.printStackTrace();
	    return null;
	}
    }
    
    
}
