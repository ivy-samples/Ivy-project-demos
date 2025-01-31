package com.axonivy.connectivity.rest.client.connect;

import static ch.ivyteam.ivy.application.RuntimeLogCategory.REST_CLIENT;

import java.util.logging.Logger;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import ch.ivyteam.ivy.application.IProcessModelVersion;

/**
 * Track sending of requests and receiving of responses using jersey-log capabilities.
 */
public class CustomLog implements Feature {

  @Override
  public boolean configure(FeatureContext context) {
    var restLog = IProcessModelVersion.current().getRuntimeLog(REST_CLIENT);
    Logger julLogger = java.util.logging.Logger.getLogger(restLog.getName());
    @SuppressWarnings({"deprecation", "restriction"})
    var filter = new org.glassfish.jersey.filter.LoggingFilter(julLogger, true);
    context.register(filter);
    return true;
  }

}
