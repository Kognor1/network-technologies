import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

class MulticastReceiver  {
    private byte[] buf = new byte[256];
    MulticastSocket socket;
    InetAddress group;
    Map<String,Boolean> dictionary = new HashMap<String,Boolean>();
    MulticastReceiver(String Ip,Integer port){
        try {
            socket = new MulticastSocket(port);
            group = InetAddress.getByName(Ip);
            socket.joinGroup(group);
            System.out.println("Create Multicast Group");
            MulticastPublisher mp = new MulticastPublisher(socket,Ip,port);
            mp.start();
           Thread a = new Thread(this::check);
           a.start();
        }catch (Exception e ) {
                e.printStackTrace();
        }
    }
    void start() {
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String addres= packet.getAddress().getHostAddress();
                String received = new String(
                        packet.getData(), 0, packet.getLength());
                if(!dictionary.containsKey(addres))
                {
                    System.out.println((addres) + " Enabled");
                }
                dictionary.put(addres,Boolean.TRUE);
                if ("end".equals(received)) {
                    break;
                }
            }
            socket.leaveGroup(group);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    void check(){
        while (true) {
            try {
                Thread.sleep(10000);
                try {
                    for (Map.Entry<String, Boolean> pair : dictionary.entrySet()) {
                        if (pair.getValue() == Boolean.FALSE) {
                            System.out.println(String.valueOf(pair.getKey()) + " Disabled");
                            dictionary.remove(pair.getKey());
                        } else {
                            dictionary.put(pair.getKey(), Boolean.FALSE);
                        }

                    }
                }catch (ConcurrentModificationException ce ) {
                    this.check();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}