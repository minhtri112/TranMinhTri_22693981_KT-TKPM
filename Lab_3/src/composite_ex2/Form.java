package composite_ex2;

public class Form extends Container{

    public Form(String id) {
        super(id);
    }
    @Override
    public void reder(String indent){
        System.out.println(indent + "<Form>");
        super.reder(indent + "  ");
        System.out.println(indent + "</Form>");
    }



}
