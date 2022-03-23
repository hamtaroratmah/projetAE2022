package be.vinci.pae.utils;

import be.vinci.pae.business.domain.dtos.DomainFactoryImpl;
import be.vinci.pae.business.domain.dtos.MemberImpl;
import be.vinci.pae.business.domain.dtos.OfferImpl;
import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.business.ucc.ItemUCC;
import be.vinci.pae.business.ucc.ItemUCCImpl;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.business.ucc.MemberUCCImpl;
import be.vinci.pae.dal.DalServicesImpl;
import be.vinci.pae.dal.ItemDaoImpl;
import be.vinci.pae.dal.MemberDaoImpl;
import be.vinci.pae.dal.OfferDao;
import be.vinci.pae.dal.OfferDaoImpl;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.ItemDao;
import be.vinci.pae.dal.interfaces.MemberDao;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

@Provider
public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    bind(MemberImpl.class).to(Member.class).in(Singleton.class);
    bind(MemberUCCImpl.class).to(MemberUCC.class).in(Singleton.class);
    bind(MemberDaoImpl.class).to(MemberDao.class).in(Singleton.class);
    bind(DomainFactoryImpl.class).to(DomainFactory.class).in(Singleton.class);
    bind(DalServicesImpl.class).to(DalServices.class).in(Singleton.class);
    bind(ItemUCCImpl.class).to(ItemUCC.class).in(Singleton.class);
    bind(ItemDaoImpl.class).to(ItemDao.class).in(Singleton.class);
    bind(OfferImpl.class).to(OfferDTO.class).in(Singleton.class);
    bind(OfferDaoImpl.class).to(OfferDao.class).in(Singleton.class);
  }
}