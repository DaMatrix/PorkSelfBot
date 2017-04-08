package tk.daporkchop.porkselfbot;

import com.google.common.cache.CacheBuilder;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.impl.GameImpl;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import tk.daporkchop.porkselfbot.command.CommandRegistry;
import tk.daporkchop.porkselfbot.command.base.CommandPing;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by daporkchop on 05.03.17.
 */
public class PorkSelfBot {

    public static PorkSelfBot INSTANCE;
    public static Logger logger;

    public JDA jda;

    /**
     * The bot's main cache!
     */
    public ConcurrentMap<Object, Object> botCache = CacheBuilder.newBuilder()
            .maximumSize(5000)
            .expireAfterWrite(30, TimeUnit.MINUTES)
            .build().asMap();

    public PorkSelfBot()    {
        logger.info("Starting PorkSelfBot...");
        try {
            jda = new JDABuilder(AccountType.CLIENT)
                    .setToken(getToken())
                    .addListener(new PorkListener())
                    .buildBlocking();
        } catch (LoginException e)  {
            e.printStackTrace();
            System.exit(0);
        } catch (InterruptedException e)    {
            e.printStackTrace();
            System.exit(0);
        } catch (RateLimitedException e)    {
            e.printStackTrace();
            System.exit(0);
        }
    }

    public static void main(String[] args)  {
        logger = Logger.getLogger("PorkSelfBot");
        INSTANCE = new PorkSelfBot();
        INSTANCE.start();
    }

    public static String getToken() {
        File f = new File(System.getProperty("user.dir") + "/discordtoken.txt");
        String token = "";

        if (!f.exists()) {
            try {
                PrintWriter writer = new PrintWriter(f.getAbsolutePath(), "UTF-8");
                Scanner s = new Scanner(System.in);

                logger.info("Please enter your discord bot token");
                token = s.nextLine();
                writer.println(token);
                logger.info("Successful. Starting...");

                s.close();
                writer.close();
            } catch (FileNotFoundException e) {
                logger.severe("impossible error kek");
                e.printStackTrace();
                System.exit(0);
            } catch (UnsupportedEncodingException e) {
                logger.severe("File encoding not supported!");
                e.printStackTrace();
                System.exit(0);
            }
        } else {
            try {
                Scanner s = new Scanner(f);

                token = s.nextLine();

                s.close();
            } catch (FileNotFoundException e) {
                logger.severe("impossible error kek");
                e.printStackTrace();
                System.exit(0);
            }
        }

        return token.trim();
    }

    public void start() {
        //jda.getPresence().setGame(new GameImpl("Say ..help", "https://www.twitch.tv/daporkchop_", Game.GameType.TWITCH));

        CommandRegistry.registerCommand(new CommandPing());
    }
}
