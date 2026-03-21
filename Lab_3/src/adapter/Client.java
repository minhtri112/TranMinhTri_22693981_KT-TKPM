package adapter;

public class Client {
    public static void main(String[] args) {
        JsonService service = new Adapter(new XmlService());
        String result = service.processJson("{name : Tri}");
        System.out.println(result);
    }
}
