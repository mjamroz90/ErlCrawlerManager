package pl.edu.agh.ecm.domain;


import com.mysql.jdbc.TimeUtil;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.joda.time.Period;
import org.springframework.format.annotation.NumberFormat;
import pl.edu.agh.ecm.web.util.TimeUtils;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 09.10.12
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ecm_init_urls")
public class InitUrl implements Serializable {

    private Long id;
    private String address;
    private Integer width;
    private Integer depth;
    private Long validityTime;
    private Policy policy;

    public InitUrl(){}

    public InitUrl(String address,Integer width, Integer depth,Long validityTime){

        this.address = address;
        this.width = width;
        this.depth = depth;
        this.validityTime = validityTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "address")
//    @NotEmpty(message = "{validation.NotEmpty.message}")
//    @URL(message = "{url.validation.message}")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "width")
//    @NotNull(message = "{validation.NotEmpty.message}")
//    @NumberFormat(style = NumberFormat.Style.NUMBER)
//    @Min(value = 0)
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @Column(name = "depth")
//    @NotNull(message = "{validation.NotEmpty.message}")
//    @NumberFormat(style = NumberFormat.Style.NUMBER)
//    @Min(value = 0)
    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    @Column(name = "validity_time")
//    @NumberFormat(style = NumberFormat.Style.NUMBER)
//    @Min(value = 0)
    public Long getValidityTime() {
        return validityTime;
    }

    public void setValidityTime(Long validityTime) {
        this.validityTime = validityTime;
    }

    @ManyToOne
    @JoinColumn(name = "policy_id")
    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    @Transient
    public String getValidityTimeAsString(){
        return TimeUtils.getTimeLongAsString(validityTime);
    }

    @Override
    public String toString(){
        return String.format("address : %s, width : %s, depth : %s, valid before : %s",
                address,width,depth,getValidityTimeAsString());

    }
}
