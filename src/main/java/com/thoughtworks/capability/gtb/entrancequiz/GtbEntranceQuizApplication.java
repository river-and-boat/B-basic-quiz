package com.thoughtworks.capability.gtb.entrancequiz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// GTB: + 整体上非常优秀！个别点上有点小问题，瑕不掩瑜。
// GTB: Repository 接口的定义和实现应该属于不同的包

@SpringBootApplication
public class GtbEntranceQuizApplication {
	public static void main(String[] args) {
		SpringApplication.run(GtbEntranceQuizApplication.class, args);
	}
}
