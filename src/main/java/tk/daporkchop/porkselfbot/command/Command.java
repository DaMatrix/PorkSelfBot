package tk.daporkchop.porkselfbot.command;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public abstract class Command {
	
	public String prefix;
	
	public Command(String prefix)	{
		this.prefix = prefix;
	}
	
	/**
	 * Does command logic!
	 * @param evt 
	 * 		The MessageReceivedEvent to be parsed
	 */
	public abstract void excecute(MessageReceivedEvent evt, String[] split, String rawContent);

    /**
     * Gets the command's usage
     * @return
     */
	public abstract String getUsage();

	/**
	 * Gets and example of using the command
	 * @return
	 */
	public abstract String getUsageExample();

	public void sendErrorMessage(MessageReceivedEvent evt, String message)	{
		evt.getMessage().editMessage((message == null ? "" : message + "\n") + "Usage: `" + getUsage() + "`\nExample: `" + getUsageExample() + "`").queue();
	}
}
