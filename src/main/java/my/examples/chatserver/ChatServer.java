package my.examples.chatserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private int port;
    private ChatHouse chatHouse;

    public ChatServer(int port){
        this.port = port;
        chatHouse = new ChatHouse();
    }

    public void run(){
        ServerSocket serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("접속되었습니다.");
                ChatServerHandler chatServerHandler = new ChatServerHandler(socket, chatHouse);
                chatServerHandler.start();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{    serverSocket.close();   }catch (Exception ignore){  }
        }
    }
}


class ChatServerHandler extends Thread{
    private Socket socket;
    private ChatHouse chatHouse;
    private boolean inRoom;

    public ChatServerHandler(Socket socket, ChatHouse chatHouse){
        this.socket = socket;
        this.chatHouse = chatHouse;
        this.inRoom = false;
    }

    @Override
    public void run() {
        ChatUser chatUser = new ChatUser(socket);
        try{
            String nickName = chatUser.read();
            chatUser.setNickName(nickName);
            chatHouse.addChatUser(chatUser);

            while(true){
                if(!inRoom){
                    chatUser.write("============ 메뉴 ===========");
                    chatUser.write("/create [방이름]");
                    chatUser.write("/list");
                    chatUser.write("/join [방번호]");
                    chatUser.write("/quit");
                    chatUser.write("=============================");

                }
                // 메세지를 읽어온다.
                String msg = chatUser.read();
                System.out.println("msg : "+ msg);
                // 로비에 있을 경우.
                if(!inRoom){
                    // create room
                    if(msg.indexOf("/create") == 0){
                        String title = msg.substring(msg.indexOf(" ")+1);
                        chatHouse.createChatRoom(chatUser, title);
                        inRoom = true;

                        chatUser.write(chatUser.getNickName() + "님이 " + title + "방에 입장하셨습니다.");
                    // room list
                    }else if(msg.indexOf("/list") == 0){
                        List<ChatRoom> chatRooms = chatHouse.getChatRooms();
                        int index = 0;
                        for(ChatRoom cr : chatRooms){
                            chatUser.write(index + " : "+cr.getTitle());
                            index++;
                        }
                    // quit
                    }else if(msg.indexOf("/quit") == 0){
                    // join roomnumber
                    }else if(msg.indexOf("/join") == 0){
                        int index = Integer.parseInt(msg.substring(msg.indexOf(" ")+1));
                        chatHouse.joinChatRoom(chatUser, index);
                        inRoom = true;

                        chatUser.write(chatUser.getNickName() + "님이 " + "방에 입장하셨습니다.");
                    }
                }else{  // 방에 있을 경우.
                    for(ChatUser cu : chatHouse.getUsers(chatUser)){
                        cu.write(chatUser.getNickName() + " : " + msg);
                    }
                }


            }



        }catch (Exception ex){
            chatHouse.exit(chatUser);
            ex.printStackTrace();
        }finally {
            chatUser.close();
        }
    }
}
