package pl.edu.agh.ecm.webflow.forms;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.joda.time.DateTime;
import org.springframework.format.annotation.NumberFormat;

import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: mjamroz
 * Date: 21.10.12
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
public class InitUrlForm implements Serializable {

    private String address;
    private Integer width;
    private Integer depth;
    private DateTime validityDate;

    public InitUrlForm(){}

    public InitUrlForm(String address, Integer width, Integer depth, DateTime validityDate) {
        this.address = address;
        this.width = width;
        this.depth = depth;
        this.validityDate = validityDate;
    }

    @NotEmpty(message = "{validation.NotEmpty.message}")
    @URL(message = "{url.validation.message}")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NotNull(message = "{validation.NotEmpty.message}")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 0)
    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    @NotNull(message = "{validation.NotEmpty.message}")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 0)
    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    @Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    @NotNull(message = "{validation.NotEmpty.message}")
    public DateTime getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(DateTime validityDate) {
        this.validityDate = validityDate;
    }

    public String getValidityTimeString(){
        String validityDateString = "";
        if (validityDate != null){
           validityDateString  =
                   org.joda.time.format.DateTimeFormat.forPattern("H:mm").print(validityDate);
        }
        return validityDateString;
    }

    @Override
    public String toString(){

        return String.format("address : %s, width : %s, depth : %s, valid before : %s",
                address,width,depth,getValidityTimeString());


    }
}
