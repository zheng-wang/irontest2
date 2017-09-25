package io.irontest.core.runner;

import io.irontest.db.TeststepDAO;
import io.irontest.db.UtilsDAO;
import io.irontest.models.UserDefinedProperty;
import io.irontest.models.teststep.Teststep;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;

/**
 * Created by Trevor Li on 7/14/15.
 */
public class TeststepRunnerFactory {
    private static TeststepRunnerFactory instance = new TeststepRunnerFactory();

    private TeststepRunnerFactory() { }

    public static TeststepRunnerFactory getInstance() {
        return instance;
    }

    public TeststepRunner newTeststepRunner(Teststep teststep, TeststepDAO teststepDAO, UtilsDAO utisDAO,
                                            Map<String, String> implicitProperties,
                                            List<UserDefinedProperty> testcaseUDPs,
                                            TestcaseRunContext testcaseRunContext) {
        TeststepRunner runner = null;
        try {
            Class runnerClass = Class.forName("io.irontest.core.runner." + teststep.getType() + "TeststepRunner");
            Constructor<TeststepRunner> constructor = runnerClass.getConstructor();
            runner = constructor.newInstance();
            runner.setTeststep(teststep);
            runner.setTeststepDAO(teststepDAO);
            runner.setUtilsDAO(utisDAO);
            runner.setImplicitProperties(implicitProperties);
            runner.setTestcaseUDPs(testcaseUDPs);
            runner.setTestcaseRunContext(testcaseRunContext);
        } catch (Exception e) {
            throw new RuntimeException("Unable to instantiate test step runner.", e);
        }

        return runner;
    }
}