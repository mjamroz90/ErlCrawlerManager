package pl.edu.agh.ecm.webflow.validators;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.message.MessageResolver;
import pl.edu.agh.ecm.webflow.forms.PolicyForm;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 01.11.12
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
public class PolicyFormValidator {

    private int maxProcessCountVal;
    private int minProcessCountVal;
    private int maxBufferSize;
    private int minBufferSize;

    public boolean validatePolicyForm(PolicyForm policyForm,MessageContext messageContext){

        boolean foundError = false;
        if (policyForm.getBufferSize() == null){
            messageContext.addMessage(getMessageError("policy.bufferSize","validation.NotEmpty.message",null));
            foundError = true;
        }
        else if (policyForm.getBufferSize() < minBufferSize || policyForm.getBufferSize() > maxBufferSize){
            messageContext.addMessage(getMessageError("policy.bufferSize","validation.Size.message.custom",
                    new Object[]{minBufferSize,maxBufferSize}));
            foundError = true;
        }
        if (policyForm.getDefaultValidityDate() == null){
            messageContext.addMessage(getMessageError("policy.defaultValidityDate","validation.NotEmpty.message",null));
            foundError = true;
        }
        if (policyForm.getMaxProcessCount() == null){
            messageContext.addMessage(getMessageError("policy.maxProcessCount","validation.NotEmpty.message",null));
        }
        else if (policyForm.getMaxProcessCount() < minProcessCountVal || policyForm.getMaxProcessCount() > maxProcessCountVal){
            messageContext.addMessage(getMessageError("policy.maxProcessCount","validation.Size.message.custom",
                    new Object[]{minProcessCountVal,maxProcessCountVal}));
            foundError = true;
        }
        return !foundError;
    }

    public int getMaxProcessCountVal() {
        return maxProcessCountVal;
    }

    public void setMaxProcessCountVal(int maxProcessCountVal) {
        this.maxProcessCountVal = maxProcessCountVal;
    }

    public int getMinProcessCountVal() {
        return minProcessCountVal;
    }

    public void setMinProcessCountVal(int minProcessCountVal) {
        this.minProcessCountVal = minProcessCountVal;
    }

    public int getMaxBufferSize() {
        return maxBufferSize;
    }

    public void setMaxBufferSize(int maxBufferSize) {
        this.maxBufferSize = maxBufferSize;
    }

    public int getMinBufferSize() {
        return minBufferSize;
    }

    public void setMinBufferSize(int minBufferSize) {
        this.minBufferSize = minBufferSize;
    }

    private MessageResolver getMessageError(String property,String code,Object[] args){
        MessageBuilder messageBuilder = new MessageBuilder().error().source(property).code(code);
        if (args != null){
            messageBuilder = messageBuilder.args(args);
        }
        return messageBuilder.build();
    }
}
