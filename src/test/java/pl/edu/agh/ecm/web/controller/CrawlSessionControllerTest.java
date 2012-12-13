package pl.edu.agh.ecm.web.controller;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import pl.edu.agh.ecm.crawler.CrawlerConnector;
import pl.edu.agh.ecm.domain.*;
import pl.edu.agh.ecm.service.CrawlSessionService;
import pl.edu.agh.ecm.web.form.CrawlSessionGrid;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: michal
 * Date: 13.12.12
 * Time: 14:43
 * To change this template use File | Settings | File Templates.
 */
public class CrawlSessionControllerTest extends AbstractControllerTest {

    private CrawlSessionService crawlSessionService;
    private MessageSource messageSource;

    @Test
    public void testListGrid() throws Exception {

        crawlSessionService = Mockito.mock(CrawlSessionService.class);
        List<CrawlSession> sampleSessions = new LinkedList<CrawlSession>();
        CrawlSessionController crawlSessionController = new CrawlSessionController();

        ReflectionTestUtils.setField(crawlSessionController,"crawlSessionService",crawlSessionService);
        Page<CrawlSession> samplePage = prepareSamplePage(sampleSessions);
        Mockito.when(crawlSessionService.findAllByPage(Mockito.isA(PageRequest.class))).thenReturn(samplePage);

        CrawlSessionGrid sessionGrid = crawlSessionController.listGrid(1,10,"finished","asc");

        Assert.assertEquals(sessionGrid.getCurrentPage(), samplePage.getNumber() + 1);
        Assert.assertEquals(sessionGrid.getTotalPages(),samplePage.getTotalPages());
        Assert.assertEquals(sessionGrid.getTotalRecords(),samplePage.getTotalElements());
        Assert.assertThat(sessionGrid.getSessionData(), is(equalTo(sampleSessions)));
    }

    @Test
    public void testStopSession() throws Exception{

        Long id = 1L;
        Node domainNode = new Node("node1","127.0.0.1");
        User user = new User("Michal","Wacek","Michal.Wacek","haslo");
        user.setId(id);

        CrawlSession crawlSession1 = new CrawlSession(domainNode,domainNode,new Policy(),user);
        crawlSession1.setId(id);

        crawlSessionService = Mockito.mock(CrawlSessionService.class);
        Mockito.when(crawlSessionService.findByIdWithDetail(Mockito.eq(id))).thenReturn(crawlSession1);
        messageSource = Mockito.mock(MessageSource.class);
        CrawlerConnector connector = Mockito.mock(CrawlerConnector.class);
        Mockito.when(connector.stopSession(true,crawlSession1)).thenReturn(true);
        Mockito.when(crawlSessionService.save(crawlSession1)).thenReturn(crawlSession1);
        Mockito.when(messageSource.getMessage("label_session_stop_success",
                new Object[]{},Locale.ENGLISH)).thenReturn("success");

        UserDetailsAdapter detailsAdapterMock = Mockito.mock(UserDetailsAdapter.class);
        Mockito.when(detailsAdapterMock.getUser()).thenReturn(user);

        CrawlSessionController sessionController = new CrawlSessionController();

        ReflectionTestUtils.setField(sessionController,"crawlSessionService",crawlSessionService);
        ReflectionTestUtils.setField(sessionController,"messageSource",messageSource);
        ReflectionTestUtils.setField(sessionController,"userDetailsAdapter",detailsAdapterMock);
        ReflectionTestUtils.setField(sessionController,"crawlerConnector",connector);

        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        String result = sessionController.stopSession(id,new ExtendedModelMap(),redirectAttributes,Locale.ENGLISH);
        Assert.assertNotNull(result);
        Assert.assertEquals(result,"redirect:/sessions");
    }


    private Page<CrawlSession> prepareSamplePage(List<CrawlSession> sampleSessionResult){

        Node domainNode = new Node("node1","127.0.0.1");
        CrawlSession crawlSession1 = new CrawlSession(domainNode,domainNode,new Policy(),new User());
        CrawlSession crawlSession2 = new CrawlSession(domainNode,domainNode,new Policy(),new User());
        CrawlSession crawlSession3 = new CrawlSession(domainNode,domainNode,new Policy(),new User());

        sampleSessionResult.add(crawlSession1);
        sampleSessionResult.add(crawlSession2);
        sampleSessionResult.add(crawlSession3);

        Page<CrawlSession> result = new PageImpl<CrawlSession>(sampleSessionResult);
        return result;
    }
}
