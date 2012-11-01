package pl.edu.agh.ecm.domain;

import com.mysql.jdbc.TimeUtil;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;
import org.joda.time.Period;
import org.springframework.format.annotation.NumberFormat;
import pl.edu.agh.ecm.web.util.TimeUtils;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 02.09.12
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "ecm_policies")
public class Policy implements Serializable {

    private Long id;
    private User createdBy;
    private Long defaultValidityTime;
    private Integer maxProcessCount;
    private Integer bufferSize;
    private Set<InitUrl> initUrls = new HashSet<InitUrl>();

    public Policy(){}

    public Policy(Integer maxProcessCount,Integer bufferSize, User createdBy){

        this.maxProcessCount = maxProcessCount;
        this.bufferSize = bufferSize;
        this.createdBy = createdBy;
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

    @ManyToOne
    @JoinColumn(name = "created_by")
    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "default_validity_time")
//    @NumberFormat(style = NumberFormat.Style.NUMBER)
//    @Min(value = 0)
    public Long getDefaultValidityTime() {
        return defaultValidityTime;
    }

    public void setDefaultValidityTime(Long validityTime) {
        this.defaultValidityTime = validityTime;
    }

    @Column(name = "max_process_count")
//    @NotNull(message = "{validation.NotEmpty.message}")
//    @NumberFormat(style = NumberFormat.Style.NUMBER)
//    @Max(value = 250)
//    @Min(value = 1)
    public Integer getMaxProcessCount() {
        return maxProcessCount;
    }

    public void setMaxProcessCount(Integer maxProcessCount) {
        this.maxProcessCount = maxProcessCount;
    }

    @Column(name = "buffer_size")
//    @NotNull(message = "{validation.NotEmpty.message}")
//    @NumberFormat(style = NumberFormat.Style.NUMBER)
//    @Max(value = 20000)
//    @Min(value = 1)
    public Integer getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(Integer bufferSize) {
        this.bufferSize = bufferSize;
    }

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "policy")
    public Set<InitUrl> getInitUrls() {
        return initUrls;
    }

    public void setInitUrls(Set<InitUrl> initUrls) {
        this.initUrls = initUrls;
    }

    public void addInitUrl(InitUrl initUrl){
        getInitUrls().add(initUrl);
    }

    @Transient
    public String getDefaultValidityTimeAsString(){
        return TimeUtils.getTimeLongAsString(defaultValidityTime);
    }
}
