package pizzamake;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import pizzamake.external.MakeService;
import java.util.List;

@Entity
@Table(name="Order_table")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String pizzaname;
    private Long qty;
    private String status;

    @PostPersist
    public void onPostPersist(){
        Ordered ordered = new Ordered();
        BeanUtils.copyProperties(this, ordered);
        ordered.publishAfterCommit();
    }

    @PreUpdate
    public void onPreUpdate() throws Exception {

        System.out.println("orderCanceled.onPreUpdate(\"CANCELLED\")");

        Made made = OrderApplication.applicationContext.getBean(MakeService.class).cancelMakeService(this.getId());

        System.out.println("##### Order onPreUpdate : " + made.getStatus());

        if( !"DELIVER".equals(made.getStatus()) || !"MADE".equals(made.getStatus())) {
            OrderCanceled orderCanceled = new OrderCanceled();
            BeanUtils.copyProperties(this, orderCanceled);
            orderCanceled.publishAfterCommit();
        } else {
            throw new Exception("만드는 중 입니다. 취소가 불가능 합니다.");
        }

//        OrderCanceled orderCanceled = new OrderCanceled();
//        BeanUtils.copyProperties(this, orderCanceled);
//        orderCanceled.publishAfterCommit();
//
//        //Following code causes dependency to external APIs
//        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.
//
//        pizzamake.external.Make make = new pizzamake.external.Make();
//        // mappings goes here
//        OrderApplication.applicationContext.getBean(pizzamake.external.MakeService.class)
//            .cancelMake(make);


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPizzaname() {
        return pizzaname;
    }

    public void setPizzaname(String pizzaname) {
        this.pizzaname = pizzaname;
    }
    public Long getQty() {
        return qty;
    }

    public void setQty(Long qty) {
        this.qty = qty;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
