package com.yihe.web.controller.system;

import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;

public class GoEasySdkTest {
	 public static void main(String[] args) {
	        GoEasy goEasy = new GoEasy("rest-hangzhou.goeasy.io", "BC-b81f78e64733485897d22cabc849c07c");
	        goEasy.publish("demo", "HelloWorld", new PublishListener(){
	            @Override
	            public void onSuccess() {
	                System.out.println("Publish Success");
	            }

	            @Override
	            public void onFailed(GoEasyError error) {
	                System.out.println("Publish failed "+error.getCode()+":"+error.getContent());
	            }
	        });
	    }
}
