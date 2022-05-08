package be.vinci.pae.business.ucc;

import be.vinci.pae.business.domain.interfacesdto.InterestDTO;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;

public interface InterestUcc {
   InterestDTO getInterest(int idInterest);

   ArrayList<InterestDTO> listInterests(int idItem);

}
