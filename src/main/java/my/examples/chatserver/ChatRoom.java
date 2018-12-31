package my.examples.chatserver;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    List<ChatUser> chatUsers;
    String title;

    public ChatRoom(ChatUser chatUser, String title){
        chatUsers = new ArrayList<>();
        chatUsers.add(chatUser);
        this.title = title;
    }

    public List<ChatUser> getChatUsers() {
        return chatUsers;
    }

    public String getTitle() {
        return title;
    }

    public void addChatUser(ChatUser chatUser){
        chatUsers.add(chatUser);
    }

    public void removeChatUser(ChatUser chatUser){
        chatUsers.remove(chatUsers);
    }

    public boolean existChatUser(ChatUser chatUser){
        return chatUsers.contains(chatUser);
    }

}
