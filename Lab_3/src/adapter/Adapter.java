package adapter;

public class Adapter implements JsonService{
    private XmlService xmlService;

    public Adapter(XmlService xmlService) {
        this.xmlService = xmlService;
    }

    @Override
    public String processJson(String json) {
        // JSON -> XML
        String xml = "<data>" + json + "</data>";
        // gọi hệ thống XML
        String xmlResult = xmlService.processXml(xml);
        // XML -> JSON
        String jsonResult = "{ \"data\": \"" + xmlResult + "\" }";
        return jsonResult;
    }

}
