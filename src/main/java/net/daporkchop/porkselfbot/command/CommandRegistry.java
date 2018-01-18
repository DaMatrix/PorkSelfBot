/*
 * Adapted from the Wizardry License
 * Copyright (c) 2017 DaPorkchop_
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it. Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.daporkchop.porkselfbot.command;

import java.util.HashMap;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class CommandRegistry {
	
	/**
	 * A HashMap containing all the commands and their prefix
	 */
	private static final HashMap<String, Command> COMMANDS = new HashMap<>();

	/**
	 * Registers a command to the command registry.
	 * @param cmd
	 * @return cmd again lul
	 */
	public static final Command registerCommand(Command cmd)	{
		COMMANDS.put(cmd.prefix, cmd);
		return cmd;
	}
	
	/**
	 * Runs a command
	 * @param evt
	 */
	public static void runCommand(MessageReceivedEvent evt, String rawContent)	{
		try {
			String[] split = rawContent.split(" ");
			Command cmd = COMMANDS.getOrDefault(split[0].substring(2), null);
			if (cmd != null)	{
				new Thread() {
					@Override
					public void run()	{
						cmd.excecute(evt, split, rawContent);
					}
				}.start();
			}
		} catch (Exception e)	{
			e.printStackTrace();
			evt.getMessage().editMessage("Error running command: `" + evt.getMessage().getContentRaw() + "`:\n`" + e.getClass().getCanonicalName() + "`").queue();
		}
	}
}
