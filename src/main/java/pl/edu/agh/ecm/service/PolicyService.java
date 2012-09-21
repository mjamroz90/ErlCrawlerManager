package pl.edu.agh.ecm.service;

import pl.edu.agh.ecm.domain.Policy;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 06.09.12
 * Time: 19:21
 * To change this template use File | Settings | File Templates.
 */
public interface PolicyService  {

    public Policy findById(Long id);

    public List<Policy> findByinitUrl(String initUrl);

    public List<Policy> findBycreatedBy(Long userId);

    public List<Policy> findAll();

    public Policy save(Policy policy);

}
