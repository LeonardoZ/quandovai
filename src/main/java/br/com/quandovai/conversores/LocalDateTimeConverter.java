package br.com.quandovai.conversores;
/***
 * Copyright (c) 2009 Caelum - www.caelum.com.br/opensource
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static com.google.common.base.Strings.isNullOrEmpty;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.converter.ConversionException;
import br.com.caelum.vraptor.converter.ConversionMessage;
import br.com.caelum.vraptor.converter.Converter;

/**
 * A converter for {@link LocalDateTime}.
 *
 * @author Lucas Cavalcanti
 * @author Otávio Scherer Garcia
 */
@Convert(LocalDateTime.class)
@RequestScoped
public class LocalDateTimeConverter implements Converter<LocalDateTime> {
	private static final Logger logger = LoggerFactory.getLogger(LocalDateTimeConverter.class);

	private final Locale locale;

	/**
	 * @deprecated CDI eyes only
	 */
	protected LocalDateTimeConverter() {
		this(null);
	}

	@Inject
	public LocalDateTimeConverter(Locale locale) {
		this.locale = locale;
	}

	@Override
	public LocalDateTime convert(String value, Class<? extends LocalDateTime> type) {
		if (isNullOrEmpty(value)) {
			return null;
		}
		logger.debug("LocalDateTimeValue: " + value);
		System.out.println("LocalDateTimeValue: " + value);
		try {
			return LocalDateTime.parse(value, getFormatter());
		} catch (DateTimeParseException e) {
//			throw new ConversionException(new ConversionMessage("is_not_a_valid_datetime", value));
			return LocalDateTime.parse(value, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", locale));
		}
	}

	protected DateTimeFormatter getFormatter() {
		return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(locale);
	}
}
