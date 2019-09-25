public class Main {
    static public void main(String[] arg) {
        MulticastReceiver mc = new MulticastReceiver(arg[0],Integer.valueOf(arg[1]));
        mc.start();

    }
}
