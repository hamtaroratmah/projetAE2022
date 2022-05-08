package be.vinci.pae.utils;

import be.vinci.pae.business.domain.dtos.*;
import be.vinci.pae.business.domain.interfacesbusiness.Member;
import be.vinci.pae.business.domain.interfacesdto.*;
import be.vinci.pae.business.ucc.*;
import be.vinci.pae.dal.*;
import be.vinci.pae.dal.interfaces.*;
import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

@Provider
public class ApplicationBinder extends AbstractBinder {

  @Override
  protected void configure() {
    //Member
    bind(MemberImpl.class).to(Member.class).in(Singleton.class);
    bind(MemberUCCImpl.class).to(MemberUCC.class).in(Singleton.class);
    bind(MemberDaoImpl.class).to(MemberDao.class).in(Singleton.class);

    bind(OfferUCCImpl.class).to(OfferUCC.class).in(Singleton.class);
    bind(OfferDaoImpl.class).to(OfferDao.class).in(Singleton.class);
    bind(OfferImpl.class).to(OfferDTO.class).in(Singleton.class);

    bind(ItemImpl.class).to(ItemDTO.class).in(Singleton.class);
    bind(ItemUCCImpl.class).to(ItemUCC.class).in(Singleton.class);
    bind(ItemDaoImpl.class).to(ItemDao.class).in(Singleton.class);

    //Address
    bind(AddressImpl.class).to(AddressDTO.class).in(Singleton.class);
    bind(AddressDaoImpl.class).to(AddressDao.class).in(Singleton.class);

    // Interest
    bind(InterestDaoImpl.class).to(InterestDao.class).in(Singleton.class);
    bind(InterestUccImpl.class).to(InterestUcc.class).in(Singleton.class);
    bind(InterestImpl.class).to(InterestDTO.class).in(Singleton.class);

    //Rating
    bind(RatingDaoImpl.class).to(RatingDao.class).in(Singleton.class);
    bind(RatingUccImpl.class).to(RatingUcc.class).in(Singleton.class);
    // DAL and Factory
    bind(DomainFactoryImpl.class).to(DomainFactory.class).in(Singleton.class);
    bind(DalServicesImpl.class).to(DalServices.class).to(DalBackendServices.class)
            .in(Singleton.class);
    bind(DalServicesImpl.class).to(DalServices.class).in(Singleton.class);


  }
}
