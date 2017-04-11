package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.yaml.snakeyaml.error.YAMLException;
import tk.daporkchop.porkselfbot.PorkSelfBot;
import tk.daporkchop.porkselfbot.command.Command;
import tk.daporkchop.porkselfbot.util.YMLParser;

import java.util.HashMap;

public class CommandSetStatus extends Command {

    public static final HashMap<String, OnlineStatus> nameToStatus = new HashMap<>();

    static {
        nameToStatus.put("online", OnlineStatus.ONLINE);
        nameToStatus.put("idle", OnlineStatus.IDLE);
        nameToStatus.put("dnd", OnlineStatus.DO_NOT_DISTURB);
        nameToStatus.put("donotdisturb", OnlineStatus.DO_NOT_DISTURB);
        nameToStatus.put("invisible", OnlineStatus.INVISIBLE);
        nameToStatus.put("offline", OnlineStatus.INVISIBLE);
    }

    public CommandSetStatus() {
        super("setstatus");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        try {
            YMLParser yml = new YMLParser();
            yml.loadRaw(message.substring(12));

            String statusName = yml.get("status", "asdf");
            OnlineStatus status = nameToStatus.getOrDefault(statusName, null);

            if (status == null) {
                evt.getMessage().editMessage("Unknown status! Valid values are:\n```\nonline\nidle\ndnd\ndonotdisturb\ninvisible\noffline\n```").queue();
            } else {
                PorkSelfBot.INSTANCE.jda.getPresence().setStatus(status);
                evt.getMessage().editMessage("Set status to: `" + status.getKey() + "`").queue();
            }
        } catch (YAMLException e)   {
            evt.getMessage().editMessage("Bad YML formatting!").queue();
        }
    }

    @Override
    public String getUsage() {
        return ",,setstatus";
    }

    @Override
    public String getUsageExample()	{
        return ",,setstatus";
    }
}
