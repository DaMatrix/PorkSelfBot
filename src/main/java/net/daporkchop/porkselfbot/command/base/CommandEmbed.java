/*
 * Adapted from the Wizardry License
 * Copyright (c) 2017 DaPorkchop_
 * Permission is hereby granted to any persons and/or organizations using this software to copy, modify, merge, publish, and distribute it. Said persons and/or organizations are not allowed to use the software or any derivatives of the work for commercial use or any other means to generate income, nor are they allowed to claim this software as their own.
 * The persons and/or organizations are also disallowed from sub-licensing and/or trademarking this software without explicit permission from DaPorkchop_.
 * Any persons and/or organizations using this software must disclose their source code and have it publicly available, include this license, provide sufficient credit to the original authors of the project (IE: DaPorkchop_), as well as provide a link to the original project.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.daporkchop.porkselfbot.command.base;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import org.yaml.snakeyaml.error.YAMLException;
import net.daporkchop.porkselfbot.command.Command;
import net.daporkchop.porkselfbot.util.YMLParser;

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

            evt.getMessage().delete().queue();
            evt.getChannel().sendMessage(builder.build()).queue();
            //evt.getMessage().editMessage(builder.build()).queue();
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
