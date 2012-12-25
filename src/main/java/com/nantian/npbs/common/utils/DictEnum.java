package com.nantian.npbs.common.utils;

public abstract interface DictEnum<T extends Enum<?>>{
	  public abstract String getName();

	  public abstract String getDisplay();

	  public abstract String getMemo();

	  public abstract T getDefault();
}
