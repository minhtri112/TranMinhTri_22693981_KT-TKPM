package singleton;

public class Client {
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("***Singleton Pattern Demo***\n");
        System.out.println("Trying to make a captain for your team.");
        CaptainLazy captain1 = CaptainLazy.getCaptain();
        System.out.println("Trying to make another captain for your team");
                CaptainLazy captain2 = CaptainLazy.getCaptain();
        if (captain1 == captain2){
            System.out.println("Both captain1 and captain2 are the same");
        }

//        CaptainEager captainEager1 = CaptainEager.getCaptain();
//        CaptainEager captainEager2 = CaptainEager.getCaptain();

    }
}
