package tk.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.yaml.snakeyaml.error.YAMLException;
import tk.daporkchop.porkselfbot.command.Command;
import tk.daporkchop.porkselfbot.util.YMLParser;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by daporkchop on 4/8/2017.
 */
public class CommandEmbed extends Command {

    public static final HashMap<String, Color> nameToColor = new HashMap<>();

    static {
        nameToColor.put("white", Color.WHITE);
        nameToColor.put("lightgray", Color.LIGHT_GRAY);
        nameToColor.put("gray", Color.GRAY);
        nameToColor.put("darkgray", Color.DARK_GRAY);
        nameToColor.put("black", Color.BLACK);
        nameToColor.put("white", Color.WHITE);
        nameToColor.put("red", Color.RED);
        nameToColor.put("pink", Color.PINK);
        nameToColor.put("orange", Color.ORANGE);
        nameToColor.put("yellow", Color.YELLOW);
        nameToColor.put("green", Color.GREEN);
        nameToColor.put("magenta", Color.MAGENTA);
        nameToColor.put("cyan", Color.CYAN);
        nameToColor.put("blue", Color.BLUE);
    }

    public CommandEmbed() {
        super("embed");
    }

    @Override
    public void excecute(MessageReceivedEvent evt, String[] args, String message) {
        try {
            EmbedBuilder builder = new EmbedBuilder();

            YMLParser yml = new YMLParser();
            yml.loadRaw(message.substring(8));

            String titleText = yml.get("title.text", null);
            String titleURL = yml.get("title.url", null);
            String titleIcon = yml.get("title.icon", null);
            builder.setAuthor(titleText, titleURL, titleIcon);

            String colorName = yml.get("color.name", "gray");
            if (colorName.equals("custom")) {
                int r = yml.getInt("color.r", 0);
                int g = yml.getInt("color.g", 0);
                int b = yml.getInt("color.b", 0);
                builder.setColor(new Color(r, g, b));
            } else {
                builder.setColor(nameToColor.getOrDefault(colorName, Color.GRAY));
            }

            int fieldCount = yml.getInt("fieldCount", 0);
            for (int i = 0; i < fieldCount; i++)     {
                String name = yml.getString("field" + i + ".name", "");
                String text = yml.getString("field" + i + ".text", "");
                boolean inline = yml.getBoolean("field" + i + ".inline", false);

                builder.addField(name, text, inline);
            }

            if (yml.getBoolean("showYml", false))  {
                builder.addField("YML:", "```\n" + message.substring(8) + "\n```", false);
            }

            String thumbnail = yml.get("thumbnail", null);
            if (thumbnail != null)  {
                builder.setThumbnail(thumbnail);
            }

            String image = yml.get("image", null);
            if (image != null)  {
                builder.setImage(image);
            }

            evt.getMessage().editMessage(builder.build()).queue();
        } catch (PermissionException e) {
            if (e.getPermission().ordinal() == Permission.MESSAGE_EMBED_LINKS.ordinal())    {
                evt.getMessage().editMessage("*Missing permission to send embeds!*").queue();
            }
        } catch (YAMLException e)   {
            evt.getMessage().editMessage("Bad YML formatting!").queue();
        }
    }

    @Override
    public String getUsage() {
        return ",,embed";
    }

    @Override
    public String getUsageExample()	{
        return ",,embed";
    }
}
