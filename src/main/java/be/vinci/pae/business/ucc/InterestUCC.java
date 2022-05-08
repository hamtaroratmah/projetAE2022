package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.InterestDTO;

import java.util.ArrayList;

public interface InterestUCC {
  InterestDTO getInterest(int idInterest);

  ArrayList<InterestDTO> listInterests(int idItem);
}
