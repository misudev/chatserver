package my.examples.chatserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ChatUser {
    private Socket socket;
    private String nickName;
    DataInputStream in = null;
    DataOutputStream out = null;

    public ChatUser(Socket socket){
        this.socket = socket;
        try{
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

        }catch (Exception ex){
            throw new RuntimeException("ChatUser 생성시 오류 발생.");
        }

    }
    //close()

    public void close(){
        try{    in.close(); }catch (Exception ignore){ }
        try{    out.close(); }catch (Exception ignore){ }
        try{    socket.close(); }catch (Exception ignore){ }
    }

    public Socket getSocket() {
        return socket;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public InputStream getIn() {
        return in;
    }


    public OutputStream getOut() {
        return out;
    }


    //read
    public String read(){
        try{
            return in.readUTF();
        }catch (Exception ex){
            throw new RuntimeException("메시지 읽을 때 오류..");
        }
    }
    //write
    public void write(String msg){
        try{
            out.writeUTF(msg);
            out.flush();
        }catch (Exception ex){
            throw new RuntimeException("메시지 입력 시 오류");
        }
    }
}
