package betBot.config;

import betBot.commandService.commandHandler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class MechanicsConfig {

    private final ApplicationContext context;

    @Autowired
    public MechanicsConfig(ApplicationContext context) {
        this.context = context;
    }

    @Bean
    public HashMap<String, CommandHandler> commandName2Handler() {
        HashMap<String, CommandHandler> handlers = new HashMap<>();
        handlers.put("/login", (LoginCommandHandler)context.getBean("loginCommandHandler"));
        handlers.put("/add_login", (AddUserCommandHandler)context.getBean("addUserCommandHandler"));
        handlers.put("/user_list", (UserListCommandHandler)context.getBean("userListCommandHandler"));
        handlers.put("/login_list", (LoginListCommandHandler)context.getBean("loginListCommandHandler"));
        return handlers;
    }
}
