package pl.edu.agh.ecm.webflow.forms;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.joda.time.Period;
import org.springframework.format.annotation.NumberFormat;

import pl.edu.agh.ecm.web.util.TimeUtils;

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
    private Period validityDate;
    private Integer subDomainBreadth;
    private Integer subDomainDepth;
    private Period subDomainValidityDate;

    public InitUrlForm(){}

    public InitUrlForm(String address, Integer width, Integer depth, Period validityDate) {
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

    @NotNull(message = "{validation.NotEmpty.message}")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 0)
    public Integer getSubDomainBreadth() {
        return subDomainBreadth;
    }

    public void setSubDomainBreadth(Integer subDomainBreadth) {
        this.subDomainBreadth = subDomainBreadth;
    }

    @NotNull(message = "{validation.NotEmpty.message}")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Min(value = 0)
    public Integer getSubDomainDepth() {
        return subDomainDepth;
    }

    public void setSubDomainDepth(Integer subDomainDepth) {
        this.subDomainDepth = subDomainDepth;
    }

    @NotNull(message = "{validation.NotEmpty.message}")
    public Period getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(Period validityDate) {
        this.validityDate = validityDate;
    }

    @NotNull(message = "{validation.NotEmpty.message}")
    public Period getSubDomainValidityDate() {
        return subDomainValidityDate;
    }

    public void setSubDomainValidityDate(Period subDomainValidityDate) {
        this.subDomainValidityDate = subDomainValidityDate;
    }

    public String getValidityTimeAsString(){
       return TimeUtils.getTimePeriodAsString(validityDate);
    }

    public String getSubDomainValidityTimeAsString(){
        return TimeUtils.getTimePeriodAsString(subDomainValidityDate);
    }

    @Override
    public String toString(){

//        return String.format("address : %s, width : %s, depth : %s, valid before : %s",
//                address,width,depth,getValidityTimeString());

        return String.format("address : %s, width/subdomain-width : %s/%s, depth/subdomain-depth : %s/%s, " +
                "valid before/subdomain-valid before : %s/%s",
                address,width,subDomainBreadth,depth,subDomainDepth,getValidityTimeAsString(),getSubDomainValidityTimeAsString());


    }

    public InitUrlForm copy(){
        InitUrlForm result = new InitUrlForm(this.address,this.width,this.depth,this.validityDate);
        result.setSubDomainBreadth(this.subDomainBreadth);
        result.setSubDomainDepth(this.subDomainDepth);
        result.setSubDomainValidityDate(this.subDomainValidityDate);
        return result;
    }
}
