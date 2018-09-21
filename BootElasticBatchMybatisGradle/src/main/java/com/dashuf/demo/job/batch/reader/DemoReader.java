package com.dashuf.demo.job.batch.reader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class DemoReader<T> implements ItemReader<T> {

	@Override
	public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		return null;
	}

}
