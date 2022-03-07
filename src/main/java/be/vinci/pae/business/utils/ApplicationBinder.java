package be.vinci.pae.business.utils;

import be.vinci.pae.business.domain.dtos.DomainFactoryImpl;
import be.vinci.pae.business.domain.dtos.MemberImpl;
import be.vinci.pae.business.domain.interfacesBusiness.Member;
import be.vinci.pae.business.domain.interfacesDTO.DomainFactory;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

@Provider
public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(MemberImpl.class).to(Member.class).in(Singleton.class);
    bind(DomainFactoryImpl.class).to(DomainFactory.class).in(Singleton.class);
  }
}