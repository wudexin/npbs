package com.nantian.npbs.core.orm;

import java.io.Serializable;

public interface GenericDao<T extends Serializable, PK extends Serializable>
		extends AbstractDao<T, PK> {

}
