package com.accp.util.email;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;

/**
 * 邮件发送测试
 * 
 * @author yuno
 *
 */
public class EmailRun {
	public static void main(String args[]) {
		// 收件人邮箱
		String to = "1799729439@qq.com";
		// 收件人昵称
		String name = "yuno";
		// 发送注册邮件模板
		try {
			Email.sendSimpleMail(to, "用户注册", EmailBoard.register(name, "http://localhost/ok.html"));
			System.out.println("====================\n发送成功\n====================\n");
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
			System.out.println("====================\n发送失败\n====================\n");
		}
	}
}