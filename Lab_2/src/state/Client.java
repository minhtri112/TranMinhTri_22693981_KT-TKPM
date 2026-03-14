package state;

public class Client {
    public static void main(String[] args) {
        System.out.println("***State Pattern Demonstration.***\n");
        // TV is initialized with Off state.
        Television tv = new Television();
        System.out.println("User is pressing buttons in the following sequence:");
        System.out.println("Off->Mute->On->On->Mute->Mute->On->Off\n");
        // TV is off. Pressing the off button again.
        tv.executeOffButton();
        tv.executeMuteButton();
        tv.executeOnButton();
        tv.executeOffButton();
    }
}
