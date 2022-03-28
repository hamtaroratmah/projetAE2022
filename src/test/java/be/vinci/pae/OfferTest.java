package be.vinci.pae;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import be.vinci.pae.business.domain.interfacesdto.OfferDTO;
import be.vinci.pae.business.ucc.OfferUCC;
import be.vinci.pae.dal.OfferDao;
import be.vinci.pae.dal.interfaces.DalServices;
import be.vinci.pae.utils.Config;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class OfferTest {

  private DalServices dalServices;

  private OfferUCC offerUCC;
  private OfferDTO offerDTO;
  private OfferDao offerDao;

  @BeforeAll
  void initAll() {
    Config.load("dev.properties");
    ServiceLocator locator = ServiceLocatorUtilities.bind(new ApplicationBinderTest());
    dalServices = locator.getService(DalServices.class);

    offerUCC = locator.getService(OfferUCC.class);
    offerDao = locator.getService(OfferDao.class);
    offerDTO = locator.getService(OfferDTO.class);
    Mockito.when(offerDTO.getIdOffer()).thenReturn(1);
    Mockito.when(offerDao.getOffer(offerDTO.getIdOffer())).thenReturn(offerDTO);
  }

  @DisplayName("Tests OfferUCC")
  @Test
  public void getOffer() {
    assertAll(
        () -> assertEquals(offerDTO, offerUCC.getOffer(offerDTO.getIdOffer()))
//        () -> assertThrows(FatalException.class, () -> offerUCC.getOffer(0))
    );
  }

}
