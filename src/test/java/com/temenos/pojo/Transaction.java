package com.temenos.pojo;

import java.util.Comparator;
import java.util.Date;

public class Transaction {

	private String account;

	private String desc;

	private String currency;

	private Double amount;

	private Date date;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return this.account + " : " +  this.date + " : " + this.desc +  " : " + this.currency +  " : " + this.amount;  
	}

	public static Comparator<Transaction> descriptionAsc = new Comparator<Transaction>() {		
		@Override
		public int compare(Transaction arg0, Transaction arg1) {
			return arg0.desc.compareTo(arg1.desc);
		}
	};
	
	public static Comparator<Transaction> descriptionDesc = new Comparator<Transaction>() {		
		@Override
		public int compare(Transaction arg0, Transaction arg1) {
			return arg1.desc.compareTo(arg0.desc);
		}
	};
	
	public static Comparator<Transaction> accountAsc = new Comparator<Transaction>() {		
		@Override
		public int compare(Transaction arg0, Transaction arg1) {
			return arg0.account.compareTo(arg1.account);
		}
	};
	
	public static Comparator<Transaction> accountDesc = new Comparator<Transaction>() {		
		@Override
		public int compare(Transaction arg0, Transaction arg1) {
			return arg1.account.compareTo(arg0.account);
		}
	};
	
	public static Comparator<Transaction> currencyAsc = new Comparator<Transaction>() {		
		@Override
		public int compare(Transaction arg0, Transaction arg1) {
			return arg0.currency.compareTo(arg1.currency);
		}
	};
	
	public static Comparator<Transaction> currencyDesc = new Comparator<Transaction>() {		
		@Override
		public int compare(Transaction arg0, Transaction arg1) {
			return arg1.currency.compareTo(arg0.currency);
		}
	};
	
	public static Comparator<Transaction> dateAsc = new Comparator<Transaction>() {		
		@Override
		public int compare(Transaction arg0, Transaction arg1) {
			return arg0.date.compareTo(arg1.date);
		}
	};
	
	public static Comparator<Transaction> dateDesc = new Comparator<Transaction>() {		
		@Override
		public int compare(Transaction arg0, Transaction arg1) {
			return arg1.date.compareTo(arg0.date);
		}
	};
	
	public static Comparator<Transaction> amountAsc = new Comparator<Transaction>() {		
		@Override
		public int compare(Transaction arg0, Transaction arg1) {
			return arg0.amount.compareTo(arg1.amount);
		}
	};
	
	public static Comparator<Transaction> amountDesc = new Comparator<Transaction>() {		
		@Override
		public int compare(Transaction arg0, Transaction arg1) {
			return arg1.amount.compareTo(arg0.amount);
		}
	};
}
