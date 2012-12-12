package pl.edu.agh.ecm.webflow;

import junit.framework.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.MessageSource;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;
import pl.edu.agh.ecm.crawler.CrawlerConnector;
import pl.edu.agh.ecm.webflow.action.CrawlSessionActions;
import pl.edu.agh.ecm.webflow.validators.PolicyFormValidator;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 12.12.12
 * Time: 20:08
 * To change this template use File | Settings | File Templates.
 */
public class CrawlSessionFlowTest extends AbstractXmlFlowExecutionTests {

    private CrawlerConnector connector;
    private MessageSource messageSource;

    @Override
    protected FlowDefinitionResource getResource(FlowDefinitionResourceFactory flowDefinitionResourceFactory) {
        FlowDefinitionResource resource =
                flowDefinitionResourceFactory.createFileResource("src/main/webapp/WEB-INF/flows/startCrawlSession/startCrawlSession-flow.xml");
        Assert.assertNotNull(resource);
        return resource;
    }

    @Override
    protected void configureFlowBuilderContext(MockFlowBuilderContext builderContext){
        CrawlSessionActions actions = new CrawlSessionActions();
        builderContext.registerBean("crawlSessionActions",actions);
        connector = Mockito.mock(CrawlerConnector.class);
        actions.setCrawlerConnector(connector);
        messageSource = Mockito.mock(MessageSource.class);
        actions.setMessageSource(messageSource);
        actions.setPolicyFormValidator(new PolicyFormValidator());

    }

    @Override
    protected void registerMockFlowBeans(ConfigurableBeanFactory beanFactory){

    }


}
