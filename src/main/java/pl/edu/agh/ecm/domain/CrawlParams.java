package pl.edu.agh.ecm.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 02.09.12
 * Time: 18:27
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ecm_crawl_params")
public class CrawlParams implements Serializable {

    private Long id;
    private Integer maxProcessCount;
    private Integer bufferSize;

    public CrawlParams(){}

    public CrawlParams(int maxProcessCount, int bufferSize){

        this.maxProcessCount = maxProcessCount;
        this.bufferSize = bufferSize;
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

    @Column(name = "max_process_count")
    @NotNull(message = "{validation.NotEmpty.message}")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Max(value = 250)
    public Integer getMaxProcessCount() {
        return maxProcessCount;
    }

    public void setMaxProcessCount(Integer maxProcessCount) {
        this.maxProcessCount = maxProcessCount;
    }

    @Column(name = "buffer_size")
    @NotNull(message = "Buffer size {validation.NotEmpty.message}")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Max(value = 20000)
    public Integer getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(Integer bufferSize) {
        this.bufferSize = bufferSize;
    }
}
