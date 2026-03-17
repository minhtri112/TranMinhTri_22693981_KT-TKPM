package ex2_1;

public class CancelledStatus implements PossibleStateOrder {
    @Override
    public void moveState(Order order) {
        System.out.println("Đơn hàng đang ở trạng thái đã hủy");
        System.out.println("Không chuyển trạng thái được nữa");
    }
}
