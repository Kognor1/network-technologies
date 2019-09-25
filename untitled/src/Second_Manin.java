public class Second_Manin {

    public static void main(String[] args) {
        MulticastReceiver mc = new MulticastReceiver(args[0],Integer.valueOf(args[1]));
        mc.start();

    }

}
