package com.fibi.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.log4j.Logger;

/**
 *
 * This class holds all the util functions of FIBI
 *
 * @author pragu
 */

public class FibiUtil {
	
	private static final Logger LOG = Logger.getLogger(FibiUtil.class);

	private final static DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * @param str_date
	 * @return timestamp
	 */
	public static Timestamp convertStringToTimestamp(final String str_date) {
		try {
			return str_date != null ? new Timestamp(formatter.parse(str_date).getTime()) : null;
		} catch (final ParseException e) {
			LOG.error("Exception while converting the String to Time stamp :" + e);
			return null;
		}
	}
	
	public static void revisitjava8Features() {
		
		List<Integer> myList = new ArrayList<Integer>();
		
		for(int i=0; i<10; i++)
			myList.add(i);
		
		myList.forEach(myItem->System.out.println(myItem));
		
		System.out.println("-----------------------------------");
		
		myList.forEach(myItem-> {
			if(myItem==1) {
				System.out.println(myItem);
			}
		});
		
		System.out.println("-----------------------------------");
		
		myList.forEach(System.out::println);
		
		System.out.println("-----------------------------------");
		
		myList.stream()
		      .filter(item->item.equals(3))
		      .forEach(System.out::println);
		
		System.out.println("-----------------------------------");
		
		
		Consumer<Integer> style = s -> {
			int summ = 0;
			summ = summ + s;
			System.out.println("Value:"+summ);
		};
		
		myList.forEach(style);
		
		System.out.println("-----------------------------------");
		
		MyConsumer style1 = new MyConsumer();
		
		myList.forEach(style1);
		
		System.out.println("sum : "+style1.getCount());
	
		System.out.println("-----------------------------------");
		
		myList.forEach(new Consumer<Integer>() {

			int sum = 0;
		
			@Override
			public void accept(Integer item) {
				sum = sum + item;
				System.out.println(sum);
			}
			
		});
		
		System.out.println("-----------------------------------");
		
	}
}
