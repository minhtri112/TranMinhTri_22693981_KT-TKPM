package state;

public class Television {
    PossibleState currentState;

    public Television() {
        this.currentState = new OffState();
    }

    // Some code skipped here
    public void executeOffButton() {
        System.out.println("You pressed the Off button.");
        // Delegating the state behavior9
        currentState.pressOffButton(this);
    }
    public void executeOnButton() {
        System.out.println("You pressed the On button.");
        // Delegating the state behavior
        currentState.pressOnButton(this);
    }
    public void executeMuteButton() {
        System.out.println("You pressed the Mute button.");
        // Delegating the state behavior
        currentState.pressMuteButton(this);
    }

    public PossibleState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(PossibleState currentState) {
        this.currentState = currentState;
    }
}
