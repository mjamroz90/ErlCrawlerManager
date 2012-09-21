package pl.edu.agh.ecm.service.jpa;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.agh.ecm.domain.Policy;
import pl.edu.agh.ecm.repository.PolicyRepository;
import pl.edu.agh.ecm.service.PolicyService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 06.09.12
 * Time: 19:26
 * To change this template use File | Settings | File Templates.
 */

@Service("policyService")
@Repository
@Transactional
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    PolicyRepository policyRepository;

    @Transactional(readOnly = true)
    public Policy findById(Long id) {
        return policyRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public List<Policy> findByinitUrl(String initUrl) {
       return policyRepository.findByinitUrl(initUrl);
    }

    @Transactional(readOnly = true)
    public List<Policy> findBycreatedBy(Long userId) {
        return policyRepository.findBycreatedBy(userId);
    }

    @Transactional(readOnly = true)
    public List<Policy> findAll() {
        return Lists.newArrayList(policyRepository.findAll());
    }

    public Policy save(Policy policy) {
        return policyRepository.save(policy);
    }
}
