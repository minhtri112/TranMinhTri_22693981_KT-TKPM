package composite_ex2;

public class Client {
    public static void main(String[] args) {
        Modal modal = new Modal("modal1");

        Form form = new Form("form1");

        Button submit = new Button("btn1", "Submit");
        Button cancel = new Button("btn2", "Cancel");

        Input username = new Input("input1", "admin");

        form.add(username);
        form.add(submit);
        form.add(cancel);

        modal.add(form);

        // render UI
        modal.reder("");

        System.out.println("\n--- Click test ---");
        modal.click();

        System.out.println("\n--- Disable form ---");
        form.setDisabled(true);
        modal.reder("");

        System.out.println("\n--- Find component ---");
        UIComponent found = modal.findById("btn1");
        if (found != null) {
            found.click();
        }
    }
}
