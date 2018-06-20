package org.bitbucket.r3bus.model;

public interface PropertyListener {

	public void onPropertyEvent(Object source, String name, Object oldValue, Object newValue);

}
