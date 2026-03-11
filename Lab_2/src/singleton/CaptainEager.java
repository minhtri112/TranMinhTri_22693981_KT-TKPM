package singleton;

public class CaptainEager {
    // Tao object ngay khi duoc load
    private static final CaptainEager instance = new CaptainEager();
    private CaptainEager() {
        System.out.println("CaptainEager start");
    }
    public static CaptainEager getCaptain(){
        System.out.println("\tYou already have a captain for your team");
        System.out.println("\tSend him for the toss.");
        return instance;
    }
}
