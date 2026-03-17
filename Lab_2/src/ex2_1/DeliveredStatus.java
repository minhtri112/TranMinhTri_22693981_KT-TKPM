package ex2_1;

import state.PossibleState;

public class DeliveredStatus implements PossibleStateOrder {

    @Override
    public void moveState(Order order) {
        System.out.println("Order đang ở trạng thái đã giao");
        System.out.println("Order chuyển sang trạng thái đã hủy");
        order.setPossibleStateOrder(new CancelledStatus());
    }
}
