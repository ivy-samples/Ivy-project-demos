package com.axon.ivy.engine.config;

import java.util.Properties;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import ch.ivyteam.ivy.environment.Ivy;

@ManagedBean
@ApplicationScoped
public class HelperBean
{
  boolean demoLicence = LicenceUtil.isDemo();
  
  public String getPropertiesSize(Properties properties)
  {
    if (properties.isEmpty())
    {
      return "";
    }
    return "(" + properties.size() + ")";
  }

  public boolean mustAuthenticate()
  {
    if (isNotDemoLicence() && isNotAuthenticated() && isNotServerConfigurationApplicaton())
    {
      return true;
    }
    return false;
  }

  private boolean isNotDemoLicence()
  {
    return !demoLicence;
  }

  private boolean isNotAuthenticated()
  {
    return Ivy.session().isSessionUserUnknown();
  }

  private boolean isNotServerConfigurationApplicaton()
  {
    return Ivy.wf().getApplication().getName() != "ServerConfiguration";
  }
}
