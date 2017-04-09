package com.fibi.util;

import java.util.function.Consumer;

class MyConsumer implements Consumer<Integer>{

	static int sum = 0;
	
	public void accept(Integer t) {
		System.out.println("Consumer impl Value::"+t);
		sum = sum + t;
		System.out.println(sum);
	}


	public int getCount() {
		return sum;
	}
}