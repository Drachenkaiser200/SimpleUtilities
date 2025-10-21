package me.unknown.utilities.exception;

import me.unknown.utilities.Main;

public class AlreadyACommandItemException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return Main.get.prefix + "§cThis item is already a command item. If you wish to override the existing command, use §e\"/simpleutilities setCommand true [command]\"§c.";
	}
	
}
