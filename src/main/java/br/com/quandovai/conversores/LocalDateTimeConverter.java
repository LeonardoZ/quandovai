package br.com.quandovai.conversores;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.enterprise.context.ApplicationScoped;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.converter.ConversionException;
import br.com.caelum.vraptor.converter.ConversionMessage;
import br.com.caelum.vraptor.converter.Converter;

@Convert(LocalDateTime.class)
@ApplicationScoped
public class LocalDateTimeConverter implements Converter<LocalDateTime> {

    @Override
    public LocalDateTime convert(String value, Class<? extends LocalDateTime> type) {

	try {
	    return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
	} catch (DateTimeParseException e) {
	    throw new ConversionException(new ConversionMessage("Data/Hora inválido", value));
	}
    }

}
