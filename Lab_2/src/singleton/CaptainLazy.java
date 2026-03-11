package singleton;

final class CaptainLazy {
    private static CaptainLazy captain;
    // Making the constructor private
    // to prevent the use of "new"
    private CaptainLazy() {
       System.out.println("Captain Lazy Constructor");
    }
    public static synchronized CaptainLazy getCaptain(){
        // Lazy initialization
        if(captain == null){
            captain = new CaptainLazy();
            System.out.println("\tA new captain is elected for your team.");
        }
        else {
            System.out.println("\tYou already have a captain for your team");
            System.out.println("\tSend him for the toss.");
        }
        return captain;
    }


}
