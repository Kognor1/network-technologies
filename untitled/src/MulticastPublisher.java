import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastPublisher extends Thread {
    private MulticastSocket ms ;
    private String Ip;
    private Integer port;
    MulticastPublisher(MulticastSocket soc,String ip,Integer port_)
    {
            ms = soc;
            Ip  = ip;
            port = port_;
    }
    public void run()  {
        while(true){
            try{
                Thread.sleep(5000);

                InetAddress group = InetAddress.getByName(Ip);
                String message = String.valueOf(ProcessHandle.current().pid());;
                byte[] buf = message.getBytes();
                DatagramPacket packet
                        = new DatagramPacket(buf, buf.length,group,port);

                ms.send(packet);
                 //1000 - 1 сек
            }
            catch (Exception e )
            {
                e.printStackTrace();
            }
        }

    }
}
