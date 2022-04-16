package be.vinci.pae;


import be.vinci.pae.business.domain.dtos.DomainFactoryImpl;
import be.vinci.pae.business.domain.dtos.MemberImpl;
import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.DomainFactory;
import be.vinci.pae.business.ucc.ItemUCC;
import be.vinci.pae.business.ucc.ItemUCCImpl;
import be.vinci.pae.business.ucc.MemberUCC;
import be.vinci.pae.business.ucc.MemberUCCImpl;
import be.vinci.pae.business.ucc.OfferUCC;
import be.vinci.pae.business.ucc.OfferUCCImpl;
import be.vinci.pae.dal.DalServicesImpl;
import be.vinci.pae.dal.MemberDaoImpl;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.dal.interfaces.MemberDao;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.mockito.Mockito;

@Provider
public class ApplicationBinderTest extends AbstractBinder {

  @Override
  protected void configure() {
    bind(MemberUCCImpl.class).to(MemberUCC.class).in(Singleton.class);
    bind(ItemUCCImpl.class).to(ItemUCC.class).in(Singleton.class);
    bind(OfferUCCImpl.class).to(OfferUCC.class).in(Singleton.class);
    bind(DomainFactoryImpl.class).to(DomainFactory.class).in(Singleton.class);
    bind(Mockito.mock(DalServicesImpl.class)).to(DalServices.class);
    bind(Mockito.mock(MemberDaoImpl.class)).to(MemberDao.class);
    bind(Mockito.mock(MemberImpl.class)).to(Member.class);
  }
}
