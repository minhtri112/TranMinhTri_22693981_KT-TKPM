package ex2_1;

public class Order {
    PossibleStateOrder possibleStateOrder;
    public Order(){
        System.out.println("Đang hàng đơn trạng thái mới tạo");
        this.possibleStateOrder = new CreatedStatus();
    }

    public void executeMove(){
        possibleStateOrder.moveState(this);
    }

    public PossibleStateOrder getPossibleStateOrder() {
        return possibleStateOrder;
    }
    public void setPossibleStateOrder(PossibleStateOrder possibleStateOrder) {
        this.possibleStateOrder = possibleStateOrder;
    }

}
