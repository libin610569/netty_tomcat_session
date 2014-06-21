package com.lb.mysession.model;

import io.netty.channel.ChannelHandlerContext;

import com.lb.mysession.proto.MessageProto.Message;

public class MessageObj {
	
	private Message message;
	private ChannelHandlerContext ctx;
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	
}
