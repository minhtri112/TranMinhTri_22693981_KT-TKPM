package ex2_1;

public class ProcessingStatus implements PossibleStateOrder{
    @Override
    public void moveState(Order order) {
        System.out.println("Đơn hàng ở trạng thái đang xử lí");
        System.out.println("Chuyển sang trạng thái đã giao");
        order.setPossibleStateOrder(new DeliveredStatus());
    }
}
