package Controller;
import Model.*;
import Service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
   

public class SocialMediaController {
    MessageService messageService;
    AccountService accountService;

    public SocialMediaController(){
       this.messageService=new MessageService();
       this.accountService=new AccountService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("/messages", this::getAllmessagesHandler);
        app.post("/register", this::postRigesterHandler);
        app.get("/messages/{message_id}", this::getAllmessagesByIdHandler);
        app.post("/login", this::postLoginHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageByAccountIdHandler);
        app.post("/messages", this::postMessageHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIDHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIDHandler);
        

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void getAllmessagesHandler(Context ctx) {
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }
    private void getAllmessagesByIdHandler(Context ctx) {
        
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        ctx.json(messageService.getMessageById(message_id));
        
        }
    
    private void getMessageByAccountIdHandler(Context ctx) {
       
        int account_id = Integer.parseInt(ctx.pathParam("account_id"));
        ctx.json(messageService.getMessageByAccountId(account_id));
        
    }
    private void postRigesterHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addedaccount = accountService.addAccount(account);
        if(addedaccount==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedaccount));
        }
    }
    private void postLoginHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Account login = mapper.readValue(ctx.body(), Account.class);
        Account loginUser = accountService.login(login);
        if(loginUser!=null){
            ctx.json(mapper.writeValueAsString(loginUser));
        }else{
            ctx.status(401);
        }
    }
    private void postMessageHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message addedmessage = messageService.addMessage(message);
        if(addedmessage==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addedmessage));
        }
    }
    private void deleteMessageByIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message deletedMessage=messageService.getMessageById(message_id);
        if(deletedMessage!=null){
            ctx.json(mapper.writeValueAsString(deletedMessage));
            
        }else{
            ctx.status(200);
        }
    }
        
    
    private void updateMessageByIDHandler(Context ctx) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int message_id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedMessage = messageService.updateMessage(message_id, message);
        System.out.println(updatedMessage);
        if(updatedMessage == null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(updatedMessage));
        }

    }
}