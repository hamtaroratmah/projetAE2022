package be.vinci.pae.dal.interfaces;

import be.vinci.pae.business.domain.interfacesdto.InterestDTO;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface InterestDao {

  InterestDTO getInterest(int idInterest);

  InterestDTO createInterestInstance(ResultSet resultSetInterest);

  ArrayList<InterestDTO> listInterests(int idItem);
}
