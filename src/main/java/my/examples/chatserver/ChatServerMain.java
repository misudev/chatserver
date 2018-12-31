package my.examples.chatserver;

public class ChatServerMain {
    public static void main(String[] args){
        ChatServer chatServer = new ChatServer(9999);
        chatServer.run();
    }
}
