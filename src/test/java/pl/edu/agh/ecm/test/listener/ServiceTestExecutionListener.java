package pl.edu.agh.ecm.test.listener;

import org.dbunit.IDatabaseTester;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Michal
 * Date: 07.10.12
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */

public class ServiceTestExecutionListener implements TestExecutionListener {

    private IDatabaseTester databaseTester;

    @Override
    public void beforeTestClass(TestContext testContext) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void prepareTestInstance(TestContext testContext) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void beforeTestMethod(TestContext testContext) throws Exception {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        if (databaseTester != null){
            databaseTester.onTearDown();
        }
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {

    }
}
