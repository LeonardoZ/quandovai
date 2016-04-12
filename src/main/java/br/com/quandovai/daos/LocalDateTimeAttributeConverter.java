package br.com.quandovai.daos;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import javax.persistence.AttributeConverter;

public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Date> {

    @Override
    public Date convertToDatabaseColumn(LocalDateTime attribute) {
	if (attribute == null)
	    return null;
	Instant instant = attribute.toInstant(ZoneOffset.UTC);
	Date fromInstant = Date.from(instant);
	return fromInstant;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Date dbData) {
	if (dbData == null)
	    return null;
	Instant instant = Instant.ofEpochMilli(dbData.getTime());
	return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

}
