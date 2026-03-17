package ex2_3;


public abstract class CalFee extends Order{
   protected Order order;
   public CalFee(Order order) {
      this.order = order;
   }
}
