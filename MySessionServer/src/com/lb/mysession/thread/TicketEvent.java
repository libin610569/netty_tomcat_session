package com.lb.mysession.thread;

import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;

import com.lb.mysession.model.MessageObj;
import com.lb.mysession.proto.MessageProto.Message;


public class TicketEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7163070002185653449L;
	
	private long _sequence;
	 

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

	public long getSequence() {
		return this._sequence;
	}

	public void setSequence(long value) {
		this._sequence = value;
	}
	 

    // �������л���ʱ��,���Ժ����������
  
	public void copyTo(TicketEvent other)
	{
		other.message = this.message;
		other.ctx = this.ctx;
		other._sequence = this._sequence;
	}
	
	
}
