package me.unknown.utilities.exception;

import me.unknown.utilities.Main;

public class NoCommandItemException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return Main.get.prefix + "§cThis item is not a command item.";
	}
	
}
