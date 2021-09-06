package com.devcamp.shop24h.getquery;

import java.util.Date;

public interface GetOrderWithCustomer {
	public int getId();
	public String getComments();
	public Date getOrderDate();
	public Date getShippedDate();
	public Date getRequiredDate();
	public String getStatus();
	public String getFullName();
	public String getPhoneNumber();
}
