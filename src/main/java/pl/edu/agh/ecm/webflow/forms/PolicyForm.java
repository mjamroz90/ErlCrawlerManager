package pl.edu.agh.ecm.webflow.forms;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: mjamroz
 * Date: 28.10.12
 * Time: 17:12
 * To change this template use File | Settings | File Templates.
 */
public class PolicyForm implements Serializable {

    private Period defaultValidityDate;
    private Integer maxProcessCount;
    private Integer bufferSize;

    @NotNull(message = "{validation.NotEmpty.message}")
    public Period getDefaultValidityDate() {
        return defaultValidityDate;
    }

    public void setDefaultValidityDate(Period defaultValidityDate) {
        this.defaultValidityDate = defaultValidityDate;
    }

    @NotNull(message = "{validation.NotEmpty.message}")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Max(value = 250)
    @Min(value = 1)
    public Integer getMaxProcessCount() {
        return maxProcessCount;
    }

    public void setMaxProcessCount(Integer maxProcessCount) {
        this.maxProcessCount = maxProcessCount;
    }

    @NotNull(message = "{validation.NotEmpty.message}")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Max(value = 20000)
    @Min(value = 1)
    public Integer getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(Integer bufferSize) {
        this.bufferSize = bufferSize;
    }
}
