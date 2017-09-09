/*
 * Adapted from the Wizardry License
 * Copyright (c) 2017 DaPorkchop_
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it. Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.daporkchop.porkselfbot.command.base;

import net.daporkchop.porkselfbot.command.Command;
import net.daporkchop.porkselfbot.util.YMLParser;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.yaml.snakeyaml.error.YAMLException;
import net.daporkchop.porkselfbot.PorkSelfBot;

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
