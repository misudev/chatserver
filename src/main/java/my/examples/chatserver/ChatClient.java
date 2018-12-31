package my.examples.chatserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ChatClient {
    private String ip;
    private int port;
    public ChatClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }
    public void run(){
        Socket socket = null;
        ChatUser chatUser = null;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        try{
            socket = new Socket(ip, port);
            chatUser = new ChatUser(socket);
            System.out.println("닉네임을 입력하세요 : ");
            String nickName = br.readLine();
            chatUser.setNickName(nickName);
            chatUser.write(nickName);

            ChatClientHandler chatClientHandler = new ChatClientHandler(chatUser);
            chatClientHandler.start();

            // 키보드로 입력받는 부분
            while(true){
                String msg = br.readLine();
                chatUser.write(msg);
            }


        }catch (Exception ex){
            System.out.println("연결이 끊어졌습니다..");
        }finally {
            try{    chatUser.close(); }catch (Exception igore){}
        }
    }

}

class ChatClientHandler extends Thread{
    private ChatUser chatUser;

    public ChatClientHandler(ChatUser chatUser){
        this.chatUser = chatUser;
    }

    @Override
    public void run() {
        while(true){
            String msg = chatUser.read();
            System.out.println(msg);
        }
    }
}
