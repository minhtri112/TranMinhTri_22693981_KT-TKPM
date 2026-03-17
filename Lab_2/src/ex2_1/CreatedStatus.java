package ex2_1;

public class CreatedStatus implements PossibleStateOrder {

    @Override
    public void moveState(Order order) {
        System.out.println("Order đg ở trạng thái mới tạo");
        System.out.println("Order chuyển qua trạng thái chờ xử lí");
        order.setPossibleStateOrder(new ProcessingStatus());
    }
}
