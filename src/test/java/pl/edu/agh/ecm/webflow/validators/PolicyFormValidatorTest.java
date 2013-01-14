package pl.edu.agh.ecm.webflow.validators;

import junit.framework.Assert;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageResolver;
import pl.edu.agh.ecm.webflow.forms.PolicyForm;


/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 14.01.13
 * Time: 21:21
 * To change this template use File | Settings | File Templates.
 */
public class PolicyFormValidatorTest {

    private MessageContext messageContext;
    private PolicyFormValidator policyFormValidator = new PolicyFormValidator();
    private PolicyForm policyForm = new PolicyForm();
    private int wrongNum = 0;

    @Before
    public void setUp() throws Exception {

        policyFormValidator.setMaxBufferSize(1500);
        policyFormValidator.setMaxProcessCountVal(1000);
        policyFormValidator.setMinBufferSize(1);
        policyFormValidator.setMinProcessCountVal(1);

        policyForm.setBufferSize(null);
        policyForm.setDefaultValidityDate(null);
        policyForm.setMaxProcessCount(null);
        messageContext = Mockito.mock(MessageContext.class);

        Mockito.doAnswer(new Answer<Integer>() {
            @Override
            public Integer answer(InvocationOnMock invocationOnMock) throws Throwable {
                wrongNum++;
                return wrongNum;
            }
        }).when(messageContext).addMessage(Mockito.isA(MessageResolver.class));

    }

    @Test
    public void testValidatePolicyForm() throws Exception {

        boolean result = policyFormValidator.validatePolicyForm(policyForm,messageContext);
        Assert.assertEquals(wrongNum,3);
        Assert.assertFalse(result);
        wrongNum = 0;

        policyForm.setBufferSize(150);
        policyForm.setMaxProcessCount(100);
        policyForm.setDefaultValidityDate(new Period(13,24,0,0));

        result = policyFormValidator.validatePolicyForm(policyForm,messageContext);
        Assert.assertTrue(result);
        Assert.assertEquals(wrongNum,0);

        policyForm.setBufferSize(-1);
        result = policyFormValidator.validatePolicyForm(policyForm,messageContext);
        Assert.assertFalse(result);
        Assert.assertEquals(wrongNum,1);
        wrongNum = 0;

        policyForm.setBufferSize(1600);
        result = policyFormValidator.validatePolicyForm(policyForm,messageContext);
        Assert.assertFalse(result);
        Assert.assertEquals(wrongNum,1);
        policyForm.setBufferSize(150);
        wrongNum = 0;

        policyForm.setMaxProcessCount(-1);
        result = policyFormValidator.validatePolicyForm(policyForm,messageContext);
        Assert.assertFalse(result);
        Assert.assertEquals(wrongNum,1);
        wrongNum = 0;

        policyForm.setMaxProcessCount(12000);
        result = policyFormValidator.validatePolicyForm(policyForm,messageContext);
        Assert.assertFalse(result);
        Assert.assertEquals(wrongNum,1);
        policyForm.setMaxProcessCount(100);
        wrongNum = 0;

        policyForm.setDefaultValidityDate(null);
        result = policyFormValidator.validatePolicyForm(policyForm,messageContext);
        Assert.assertFalse(result);
        Assert.assertEquals(wrongNum,1);

    }

}
