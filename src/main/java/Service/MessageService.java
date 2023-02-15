package Service;
import Model.*;
import DAO.*;


import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO=new MessageDAO();
    }
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    
}
   
    public Message addMessage(Message message){
        String text=message.getMessage_text();
        if(text.length()==0 | text.length()>255){
            return null;
        }
        
        return messageDAO.insertnewMessage(message);
}

    public Message updateMessage(int message_id, Message message){
        String text=message.getMessage_text();
         Message messageFromDb=this.messageDAO.getMessageById(message_id);
         if(messageFromDb==null)return null;
         if(text.length()==0 | text.length()>255)return null;

         messageDAO.updateMessage(message_id, message);
         return this.messageDAO.getMessageById(message_id);
}

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
}
    public Message getMessageById(int message_id) {
          return messageDAO.getMessageById(message_id);
}
    public Message deleteMessage(int message_id){

        Message messageFromDb=this.messageDAO.getMessageById(message_id);
        if(messageFromDb==null)return null;
        return messageDAO.deleteMessage(message_id);
            
}
     public List<Message> getMessageByAccountId(int account_id){
            return messageDAO.getMessageByAccountId(account_id);
}

}








