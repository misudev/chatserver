package my.examples.chatserver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatHouse {
    List<ChatUser> lobby;
    List<ChatRoom> chatRooms;

    public ChatHouse(){
        lobby = Collections.synchronizedList(new ArrayList<>());
        chatRooms = Collections.synchronizedList(new ArrayList<>());
    }

    public void addChatUser(ChatUser chatUser){
        lobby.add(chatUser);
    }

    public void exit(ChatUser chatUser){
        lobby.remove(chatUser);
    }

    public void createChatRoom(ChatUser chatUser, String title){
        chatRooms.add(new ChatRoom(chatUser, title));
        lobby.remove(chatUser);
    }

    public void joinChatRoom(ChatUser chatUser, int index){
        chatRooms.get(index).addChatUser(chatUser);
        lobby.remove(chatUser);
    }

    public List<ChatUser> getLobby() {
        return lobby;
    }

    public List<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public List<ChatUser> getUsers(ChatUser chatUser){
        for(ChatRoom ch : chatRooms){
            if(ch.existChatUser(chatUser)){
                return ch.getChatUsers();
            }
        }
        return new ArrayList<>();   // 빈 리스트를 리턴한다.
    }
}
